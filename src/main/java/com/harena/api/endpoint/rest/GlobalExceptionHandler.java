package com.harena.api.endpoint.rest;

import com.harena.api.exception.ApiErrorResponse;
import com.harena.api.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({ApiException.class})
  public ResponseEntity<ApiErrorResponse> handleRuntimeException(ApiException error) {
    return new ResponseEntity<>(
        new ApiErrorResponse(error.getMessage(), error.getStatus()), error.getStatus());
  }
}
