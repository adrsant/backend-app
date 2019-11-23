package com.luizalabs.exception;

public class NotFoundEntityException extends RuntimeException {
  private static final long serialVersionUID = 8625982241477310408L;

  public NotFoundEntityException() {
    super();
  }

  public NotFoundEntityException(String message) {
    super(message);
  }
}
