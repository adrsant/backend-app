package com.luizalabs.exception;

public class EmailClientFoundException extends RuntimeException {

  private static final long serialVersionUID = 8625982241477310408L;

  public EmailClientFoundException() {
    super("E-mail already registered!");
  }

}
