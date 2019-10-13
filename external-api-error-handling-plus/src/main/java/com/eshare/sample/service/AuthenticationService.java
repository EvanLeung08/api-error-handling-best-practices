package com.eshare.sample.service;

import com.eshare.sample.constant.HttpHeaderConsts;
import com.eshare.sample.exception.InvalidAuthenticationException;
import com.eshare.sample.util.RequestContextHolderUtil;
import org.springframework.stereotype.Service;

/**
 * Verify if authentication is valid Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@Service
public class AuthenticationService {

  public final static String TEST_PROFILE = "123456";

  public void check() {
    //Get profileId from request
    String profileId = (String) RequestContextHolderUtil
        .fetchFromHeader(HttpHeaderConsts.API_PROFILE_ID);
    if (!TEST_PROFILE.equals(profileId)) {
      throw new InvalidAuthenticationException();
    }
  }

}
