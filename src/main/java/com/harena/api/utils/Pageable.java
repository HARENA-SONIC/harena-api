package com.harena.api.utils;

import java.util.List;

public class Pageable<E> {
  private final List<E> list;

  public Pageable(List<E> list) {
    this.list = list;
  }

  public Page<E> getPage(PageRequest pageRequest) {
    int pageNumber = pageRequest.pageNumber();
    int pageSize = pageRequest.pageSize();
    int totalPage = (int) Math.ceil((double) list.size() / pageSize);
    int lastIndex = pageNumber * pageSize;
    int firstIndex = lastIndex - pageSize;

    if (firstIndex > list.size() - 1 || pageNumber <= 0) {
      throw new IndexOutOfBoundsException();
    }

    if (lastIndex >= list.size() - 1) {
      List<E> subList = List.copyOf(list.subList(firstIndex, list.size()));

      return new Page<>(
          pageNumber,
          pageNumber == 1,
          true,
          subList,
          false,
          pageNumber != 1,
          totalPage,
          pageSize,
          list.size());
    }

    List<E> subList = List.copyOf(list.subList(firstIndex, lastIndex));

    return new Page<>(
        pageNumber,
        pageNumber == 1,
        false,
        subList,
        true,
        pageNumber != 1,
        totalPage,
        pageSize,
        list.size());
  }

  public int getTotalElements() {
    return list.size();
  }
}
