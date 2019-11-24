package com.luizalabs.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {
    super("resource requested not found!");
  }
}
