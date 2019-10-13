package com.eshare.sample.filter;

import com.alibaba.fastjson.JSON;
import com.eshare.sample.common.errors.ErrorCode;
import com.eshare.sample.common.errors.ErrorCodes;
import com.eshare.sample.common.errors.ErrorResponse;
import com.eshare.sample.common.errors.ErrorResponse.ApiError;
import com.eshare.sample.constant.HttpAttributeConsts;
import com.eshare.sample.exception.InvalidAuthenticationException;
import com.eshare.sample.service.AuthenticationService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;

/**
 * Authentication Check
 *
 * Created by liangyh on 2019/9/22. Email:10856214@163.com
 */
@Slf4j
public class AuthenticationFilter implements Filter {

  private static final String NO_MESSAGE_AVAILABLE = "No message available";
  @Autowired
  private AuthenticationService authenticationService;

  /**
   * Responsible for finding the appropriate error message(s) based on the given {@linkplain
   * ErrorCode} and {@linkplain Locale}
   */
  @Autowired
  private MessageSource apiErrorMessageSource;

  /**
   * Factory to convert the given {@linkplain Exception} to an instance of {@linkplain ErrorCode}
   */
  @Autowired
  private ErrorCodes errorCodes;

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws IOException, ServletException {
    try {
      //Check if current authentication is valid
      authenticationService.check();
      chain.doFilter(req, resp);
    } catch (Exception ex) {
      this.handleException(resp, req, ex);
    }
  }

  private void handleException(ServletResponse resp, ServletRequest req,
      Exception ex) throws IOException {
    HttpServletResponse httpResp = (HttpServletResponse) resp;
    Locale locale = LocaleContextHolder.getLocale();
    ErrorCode errorCode = errorCodes.of(ex);
    ErrorResponse errorResponse = ErrorResponse
        .of(MDC.get(HttpAttributeConsts.TRACE_ID), errorCode.httpStatus(),
            toApiError(errorCode, locale));
    httpResp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    this.writeResponse(httpResp, JSON.toJSONString(errorResponse));
  }


  private void writeResponse(HttpServletResponse resp, String message) throws IOException {
    PrintWriter out = resp.getWriter();
    out.println(message);
    out.flush();
    out.close();
  }

  /**
   * Convert the passed {@code errorCode} to an instance of {@linkplain ErrorResponse} using the
   * given {@code locale}
   */
  private ApiError toApiError(ErrorCode errorCode, Locale locale) {
    String message;
    try {
      message = apiErrorMessageSource.getMessage(errorCode.code(), new Object[]{}, locale);
    } catch (NoSuchMessageException e) {
      log
          .error("Couldn't find any message for {} code under {} locale", errorCode.code(), locale);
      message = NO_MESSAGE_AVAILABLE;
    }

    return new ApiError(errorCode.code(), message);
  }

}
