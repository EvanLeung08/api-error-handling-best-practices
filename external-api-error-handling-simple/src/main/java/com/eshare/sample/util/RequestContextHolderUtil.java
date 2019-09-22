package com.eshare.sample.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContextHolderUtil {

  public static ServletRequestAttributes getRequestAttributes() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
  }


  public static ServletContext getServletContext() {
    return ContextLoader.getCurrentWebApplicationContext().getServletContext();
  }

  public static Object attribute(String key, int scope) {
    return getRequestAttributes().getAttribute(key, scope);
  }


  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  public static HttpServletResponse getResponse() {
    return getRequestAttributes().getResponse();
  }

  public static HttpSession getSession() {
    return getRequest().getSession();
  }

  public static Object fetchFromHeader(String key){
    return getRequest().getHeader(key);
  }
}