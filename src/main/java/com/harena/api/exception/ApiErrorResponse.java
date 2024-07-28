package com.harena.api.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
public class ApiErrorResponse {
  private final String message;
  private final int code;

  public ApiErrorResponse(String message, HttpStatus status) {
    this.message = message;
    this.code = status.value();
  }
}
