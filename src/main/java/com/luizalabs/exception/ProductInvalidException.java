package com.luizalabs.exception;

public class ProductInvalidException extends RuntimeException {

  public ProductInvalidException(String message) {
    super(message);
  }
}
