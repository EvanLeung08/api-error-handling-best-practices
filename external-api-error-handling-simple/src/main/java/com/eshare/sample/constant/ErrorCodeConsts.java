package com.eshare.sample.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Eumeration of API Response status codes
 *
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@AllArgsConstructor
@Getter
public enum ErrorCodeConsts {
  INVALID_REQUEST_FORMAT("E4001", "Invalid Format.The request format is invalid"),
  NOT_AUTHENTICATION_API("E4002", "Not authentication for Current API."),
  ACCOUNT_NOT_FOUND("E4003","Account not found."),
  INTERNAL_ERROR("E5000", "Internal error.");

  /**
   * error code
   */
  private String errorCode;
  /**
   * error message
   */
  private String errorMessage;

}
