package com.eshare.sample.configuration;

import com.eshare.sample.filter.AuthenticationFilter;
import com.eshare.sample.filter.GlobalFilter;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Filter registeration
 *
 * Created by liangyh on 2019/9/22. Email:10856214@163.com
 */
@Configuration
public class FilterConfiguration implements WebMvcConfigurer {

  @Bean
  public FilterRegistrationBean globalFilterRegisteration() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(this.globalFilter());
    registrationBean.addUrlPatterns("/quota/*");
    registrationBean.setOrder(1);
    return registrationBean;
  }


  @Bean
  public FilterRegistrationBean authenticationFilterRegisteration() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(this.authenticationFilter());
    registrationBean.addUrlPatterns("/quota/*");
    registrationBean.setOrder(2);
    return registrationBean;
  }


  @Bean
  public Filter globalFilter() {
    return new GlobalFilter();
  }

  @Bean
  public Filter authenticationFilter() {
    return new AuthenticationFilter();
  }

}
