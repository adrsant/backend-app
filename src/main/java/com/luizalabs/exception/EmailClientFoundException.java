package com.luizalabs.exception;

public class EmailClientFoundException extends RuntimeException {

  public EmailClientFoundException() {
    super("E-mail already registered!");
  }
}
