package com.eshare.sample.service;

import com.eshare.sample.constant.HttpAttributeConsts;
import com.eshare.sample.exception.InvalidAccountException;
import com.eshare.sample.model.dto.QuotaResponseDTO;
import com.eshare.sample.util.RequestContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;

/**
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@Slf4j
@Service
public class QuotaService {


  public QuotaResponseDTO getQuota(String customerId) {
    //For external APIs, other internal microservices are generally called to get data.

    //Call quota center to get quota information
    //Simulate to call a remote service and enconter a exception
    ResponseEntity<Object> responseEntity = callQuotaCenter2GetQuota(customerId);
    if (HttpStatus.OK == responseEntity.getStatusCode()) {
      //Return mock data
      return new QuotaResponseDTO(customerId, 100, 10);
    } else {
      //Convert response
      String traceId = (String) RequestContextHolderUtil
          .attribute(HttpAttributeConsts.TRACE_ID, RequestAttributes.SCOPE_REQUEST);
      log.error("{}:Failed to call quota center,{}", traceId, responseEntity.toString());
      throw new InvalidAccountException();
    }
  }

  /**
   * Simulate to call remote quota center service
   */
  private ResponseEntity callQuotaCenter2GetQuota(String customerId) {
    log.info("Quota Center get request parameters:{}", customerId);
    log.info("Quota Center start to process...");
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }


}
