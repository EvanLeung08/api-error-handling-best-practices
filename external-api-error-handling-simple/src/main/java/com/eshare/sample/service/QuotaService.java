package com.eshare.sample.service;

import com.alibaba.fastjson.JSON;
import com.eshare.sample.constant.ErrorCodeConsts;
import com.eshare.sample.constant.HttpAttributeConsts;
import com.eshare.sample.exception.FacadeException;
import com.eshare.sample.model.dto.ErrorResponseDTO;
import com.eshare.sample.model.dto.QuotaResponseDTO;
import com.eshare.sample.util.RequestContextHolderUtil;
import lombok.Data;
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
    ResponseEntity<Object> responseEntity = getQuatoFromQuotaCenter(customerId);
    if (HttpStatus.OK == responseEntity.getStatusCode()) {
      //Return mock data
      return new QuotaResponseDTO(customerId, 100, 10);
    } else {
      //Convert response
      String traceId = (String) RequestContextHolderUtil
          .attribute(HttpAttributeConsts.TRACE_ID, RequestAttributes.SCOPE_REQUEST);
      log.error("{}:Failed to call quota center,{}", traceId, responseEntity.toString());
      ErrorResponseDTO errorResponseDTO = exceptionToErrorCode(
          JSON.parseObject(responseEntity.getBody().toString(), RemoteErrorExsponse.class));
      throw new FacadeException(errorResponseDTO.getErrorCode(),
          errorResponseDTO.getErrorMessage());
    }
  }

  /**
   * Simulate to call remote quota center service
   */
  private ResponseEntity<Object> getQuatoFromQuotaCenter(String customerId) {
    log.info("Quota Center get request parameters:{}", customerId);
    log.info("Quota Center start to process...");
    return new ResponseEntity(JSON.toJSON(new RemoteErrorExsponse()), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle unexpected result to API Error code
   */
  private ErrorResponseDTO exceptionToErrorCode(RemoteErrorExsponse result) {
    ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
    //Conver error code to our API error code
    if ("EQC400101".equals(result.getErrorCode())) {
      errorResponseDTO.setErrorCode(ErrorCodeConsts.ACCOUNT_NOT_FOUND.getErrorCode());
      errorResponseDTO.setErrorMessage(ErrorCodeConsts.ACCOUNT_NOT_FOUND.getErrorMessage());
    } else {
      errorResponseDTO.setErrorCode(ErrorCodeConsts.INTERNAL_ERROR.getErrorCode());
      errorResponseDTO.setErrorMessage(ErrorCodeConsts.INTERNAL_ERROR.getErrorMessage());
    }
    return errorResponseDTO;
  }


}

/**
 * Mock remote response
 */
@Data
class RemoteErrorExsponse {

  private String errorCode = "EQC400101";
  private String errorMessage = "Invalid account.";
  private String errorDetails = "CustomerID 123456 can not be found in quota center.";
}
