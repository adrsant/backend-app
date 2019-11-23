package com.luizalabs.exception;

public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 8625982241477310408L;

  public ResourceNotFoundException() {
    super("resource requested not found!");
  }
}
