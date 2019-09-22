package com.eshare.sample.handler;

import com.eshare.sample.constant.ErrorCodeConsts;
import com.eshare.sample.constant.HttpAttributeConsts;
import com.eshare.sample.exception.FacadeException;
import com.eshare.sample.exception.ValidationException;
import com.eshare.sample.model.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponseDTO> handleAllException(Exception ex,
      WebRequest request) {
    //Get traceId from context
    String traceId = (String) request
        .getAttribute(HttpAttributeConsts.TRACE_ID, RequestAttributes.SCOPE_REQUEST);
    log.error("{}:{}", traceId, ex.getMessage(), ex);
    ErrorResponseDTO error = ErrorResponseDTO.builder()
        .errorCode(ErrorCodeConsts.INTERNAL_ERROR.getErrorCode())
        .errorMessage(ErrorCodeConsts.INTERNAL_ERROR.getErrorMessage())
        .traceId(traceId).build();
    return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(FacadeException.class)
  public final ResponseEntity<ErrorResponseDTO> handleFacadeException(FacadeException ex,
      WebRequest request) {
    //Get traceId from context
    String traceId = (String) request
        .getAttribute(HttpAttributeConsts.TRACE_ID, RequestAttributes.SCOPE_REQUEST);
    log.error("{}:{}", traceId, ex.getMessage(), ex);
    ErrorResponseDTO error = ErrorResponseDTO.builder()
        .errorCode(ex.getErrorCode())
        .errorMessage(ex.getErrorMessage())
        .errorDetails(ex.getDetailMessage())
        .traceId(traceId).build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ValidationException.class)
  public final ResponseEntity<ErrorResponseDTO> handleValidationException(
      ValidationException ex,
      WebRequest request) {
    //Get traceId from context
    String traceId = (String) request
        .getAttribute(HttpAttributeConsts.TRACE_ID, RequestAttributes.SCOPE_REQUEST);
    log.error("{}:{}", traceId, ex.getMessage(), ex);
    ErrorResponseDTO error = ErrorResponseDTO.builder()
        .errorCode(ex.getErrorCode())
        .errorMessage(ex.getErrorMessage())
        .errorDetails(ex.getDetailMessage())
        .traceId(traceId).build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }


}
