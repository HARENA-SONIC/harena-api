package com.harena.api.utils;

public record PageRequest(int pageNumber, int pageSize) {
  public static PageRequest of(int pageNumber, int pageSize) {
    return new PageRequest(pageNumber, pageSize);
  }
}
