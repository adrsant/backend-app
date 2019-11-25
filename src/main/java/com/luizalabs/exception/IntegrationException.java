package com.luizalabs.exception;

public class IntegrationException extends RuntimeException {

  public IntegrationException() {
    super("can not integrate to product api!");
  }
}
