package com.luizalabs.exception;

public class FoundEntityException extends RuntimeException {

  private static final long serialVersionUID = 8625982241477310408L;

  public FoundEntityException() {
    super();
  }

  public FoundEntityException(String message) {
    super(message);
  }
}
