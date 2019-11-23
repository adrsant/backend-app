package com.luizalabs.exception;

public class ClientFoundException extends RuntimeException {

  private static final long serialVersionUID = 8625982241477310408L;

  public ClientFoundException() {
    super();
  }

  public ClientFoundException(String message) {
    super(message);
  }
}
