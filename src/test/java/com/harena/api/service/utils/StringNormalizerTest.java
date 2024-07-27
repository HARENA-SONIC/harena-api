package com.harena.api.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.harena.api.utils.StringNormalizer;
import org.junit.jupiter.api.Test;

public class StringNormalizerTest {
  @Test
  void string_normalizer_test() {
    StringNormalizer subject = new StringNormalizer();
    assertEquals("patrimoine_de_joel", StringNormalizer.apply("Patrimoine de Joel"));
  }
}
