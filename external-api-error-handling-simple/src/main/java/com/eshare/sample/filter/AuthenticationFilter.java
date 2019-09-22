package com.eshare.sample.filter;

import com.alibaba.fastjson.JSON;
import com.eshare.sample.constant.HttpAttributeConsts;
import com.eshare.sample.exception.InvalidAuthenticationException;
import com.eshare.sample.model.dto.ErrorResponseDTO;
import com.eshare.sample.service.AuthenticationService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * Authentication Check
 *
 * Created by liangyh on 2019/9/22. Email:10856214@163.com
 */
@Slf4j
public class AuthenticationFilter implements Filter {

  @Autowired
  private AuthenticationService authenticationService;

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws IOException, ServletException {
    try {
      //Check if current authentication is valid
      authenticationService.check();
    } catch (InvalidAuthenticationException ex) {
      this.handleException(resp, req, ex);
    }
    chain.doFilter(req, resp);
  }

  private void handleException(ServletResponse resp, ServletRequest req,
      InvalidAuthenticationException ex) throws IOException {
    HttpServletResponse httpResp = (HttpServletResponse) resp;
    //Get traceId
    String traceId = (String) req.getAttribute(HttpAttributeConsts.TRACE_ID);
    ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder().errorCode(ex.getErrorCode())
        .errorMessage(ex.getErrorMessage()).traceId(traceId).build();
    httpResp.setStatus(HttpStatus.UNAUTHORIZED.value());
    httpResp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    this.writeResponse(httpResp, JSON.toJSONString(errorResponseDTO));
  }

  private void writeResponse(HttpServletResponse resp, String message) throws IOException {
    PrintWriter out = resp.getWriter();
    out.println(message);
    out.flush();
    out.close();
  }
}
