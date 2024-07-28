package neocode.formekouapi.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
  public BadRequestException() {
    super("Bad request", HttpStatus.BAD_REQUEST);
  }

  public BadRequestException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
