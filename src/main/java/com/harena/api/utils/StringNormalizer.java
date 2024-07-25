package com.harena.api.utils;

public class StringNormalizer {
  public static String apply(String name) {
    return name.replaceAll("\\W", "_").toLowerCase();
  }
}
