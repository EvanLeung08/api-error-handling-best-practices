package com.eshare.sample.exception;

import com.eshare.sample.constant.ErrorCodeConsts;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exception exposed for external client
 *
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@NoArgsConstructor
@Data
public class FacadeException extends RuntimeException {

  /**
   * error code
   */
  protected String errorCode;
  /**
   * error message
   */
  protected String errorMessage;
  /**
   * detail message
   */
  protected List<String> detailMessage;

  public FacadeException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public FacadeException(String errorCode, String errorMessage,List<String> detailMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.detailMessage = detailMessage;
  }

  public FacadeException(String errorCode,
      String errorMessage, Throwable cause) {
    super(errorMessage, cause);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public FacadeException(ErrorCodeConsts returnCode, Throwable cause) {
    super(returnCode.getErrorMessage(), cause);
    this.errorCode = returnCode.getErrorCode();
    this.errorMessage = returnCode.getErrorMessage();
  }

  public FacadeException(ErrorCodeConsts returnCode) {
    super(returnCode.getErrorMessage());
    this.errorCode = returnCode.getErrorCode();
    this.errorMessage = returnCode.getErrorMessage();
  }

  public FacadeException(String errorCode, String errorMessage, List<String> detailMessage, Throwable ex) {
    super(errorMessage,ex);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.detailMessage = detailMessage;
  }
}
