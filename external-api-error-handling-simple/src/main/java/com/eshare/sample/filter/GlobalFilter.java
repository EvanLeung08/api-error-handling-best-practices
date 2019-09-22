package com.eshare.sample.filter;

import com.eshare.sample.constant.HttpAttributeConsts;
import com.eshare.sample.constant.HttpHeaderConsts;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by liangyh on 2019/9/22. Email:10856214@163.com
 */
@Slf4j
public class GlobalFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain filterChain) throws IOException, ServletException {
    //Create global traceId for each Api request
    String traceId = UUID.randomUUID().toString();
    log.info("{}:Begin of Api journey.", traceId);
    //Put traceId to each request attribute
    req.setAttribute(HttpAttributeConsts.TRACE_ID, traceId);
    filterChain.doFilter(req, resp);
    log.info("{}:End of Api journey.", traceId);

  }
}
