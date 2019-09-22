package com.eshare.sample.exception;

import java.util.List;

/**
 * Invalid bean parameters Exception
 *
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
public class ValidationException extends FacadeException {

  public ValidationException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

  public ValidationException(String errorCode, String errorMessage, List<String> detailMessage) {
    super(errorCode, errorMessage, detailMessage);
  }

  public ValidationException(String errorCode, String errorMessage, List<String> detailMessage,Throwable ex) {
    super(errorCode, errorMessage, detailMessage,ex);
  }
}
