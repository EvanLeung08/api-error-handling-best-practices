package com.eshare.sample.exception;

import com.eshare.sample.constant.ErrorCodeConsts;

/**
 * Invalid Authentication Exception
 *
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
public class InvalidAuthenticationException extends FacadeException {

  public InvalidAuthenticationException(String errorCode, String errorMessage) {
    super(errorCode,errorMessage);
  }

  public InvalidAuthenticationException(ErrorCodeConsts errorCodeConsts) {
    super(errorCodeConsts);
  }
}
