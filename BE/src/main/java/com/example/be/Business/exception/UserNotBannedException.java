package com.example.be.Business.exception;

public class UserNotBannedException extends RuntimeException {
  public UserNotBannedException(Long userId) {
    super("User with ID " + userId + " is not currently banned.");
  }
}
