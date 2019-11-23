package com.luizalabs.exception;

public class ProductNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 8625982241477310408L;

  public ProductNotFoundException() {
    super();
  }

  public ProductNotFoundException(String message) {
    super(message);
  }
}
