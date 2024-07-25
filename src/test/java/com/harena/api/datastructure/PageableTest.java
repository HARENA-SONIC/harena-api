package com.harena.api.datastructure;

import com.harena.api.utils.PageRequest;
import com.harena.api.utils.Pageable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PageableTest {
  @Test
  void pagination_tests() {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 1; i <= 8; i++) {
      numbers.add(i);
    }

    Pageable<Integer> subject = new Pageable<>(numbers);

    assertEquals(8, subject.getTotalElements());
    assertEquals(List.of(1, 2, 3), subject.getPage(new PageRequest(1, 3)).data());
    assertEquals(List.of(4, 5, 6), subject.getPage(new PageRequest(2, 3)).data());
    assertEquals(List.of(7, 8), subject.getPage(new PageRequest(3, 3)).data());

    assertTrue(subject.getPage(new PageRequest(3, 3)).isLast());
    assertTrue(subject.getPage(new PageRequest(1, 3)).isFirst());

    assertThrows(IndexOutOfBoundsException.class,() -> subject.getPage(
        new PageRequest(-1, subject.getTotalElements())));

    assertThrows(IndexOutOfBoundsException.class,() -> subject.getPage(
        new PageRequest(9, subject.getTotalElements())));

    assertFalse(subject.getPage(new PageRequest(1, subject.getTotalElements())).hasPrevious());
  }
}
