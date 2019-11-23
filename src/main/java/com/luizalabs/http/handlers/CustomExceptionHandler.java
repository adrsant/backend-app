package com.luizalabs.http.handlers;

import com.luizalabs.exception.EmailClientFoundException;
import com.luizalabs.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseBody
  public ErroMessage onErrorConvertProperty(ResourceNotFoundException exception) {
    return new ErroMessage(exception.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(EmailClientFoundException.class)
  @ResponseBody
  public ErroMessage onErrorConvertProperty(EmailClientFoundException exception) {
    return new ErroMessage(exception.getMessage());
  }
}

@AllArgsConstructor
@Getter
class ErroMessage {
  private String message;
}
