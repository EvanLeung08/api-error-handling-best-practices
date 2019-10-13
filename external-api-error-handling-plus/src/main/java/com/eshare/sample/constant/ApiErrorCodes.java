package com.eshare.sample.constant;

import com.eshare.sample.common.errors.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ApiErrorCodes implements ErrorCode {

  INVALID_ACCOUNT("EQ4001", HttpStatus.BAD_REQUEST),
  INVALID_AUTHENTICATION("EQ4002", HttpStatus.BAD_REQUEST),
  VALIDATION_ERROR("EQ4003", HttpStatus.BAD_REQUEST),
  UNKNOWN_ERROR("EQ5000", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final HttpStatus httpStatus;

  ApiErrorCodes(String code, HttpStatus httpStatus) {
    this.code = code;
    this.httpStatus = httpStatus;
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public HttpStatus httpStatus() {
    return httpStatus;
  }
}