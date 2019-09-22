package com.eshare.sample.api;

import com.eshare.sample.model.dto.QuotaRequestDTO;
import com.eshare.sample.model.dto.QuotaResponseDTO;
import com.eshare.sample.service.AuthenticationService;
import com.eshare.sample.service.QuotaService;
import com.eshare.sample.util.ValidatorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Quoto Rest API
 *
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@RestController
public class QuotaController {


  private final QuotaService quotaService;
  private final AuthenticationService authenticationService;

  public QuotaController(QuotaService quotaService, AuthenticationService authenticationService) {
    this.quotaService = quotaService;
    this.authenticationService = authenticationService;
  }

  @GetMapping(value = "/quota")
  public ResponseEntity<QuotaResponseDTO> getContract(
      @RequestParam(value = "customerId") String customerId) {
    //Check authentication
    authenticationService.check();
    //Fetch quota data
    return new ResponseEntity<>(quotaService.getQuota(customerId), HttpStatus.OK);
  }

  @PostMapping(value = "/quota")
  public ResponseEntity updateContract(@RequestBody QuotaRequestDTO quotaRequestDTO) {
    //Check authentication
    authenticationService.check();
    //Validate request format
    ValidatorUtil.checkBeanParamValidate(quotaRequestDTO);
    return new ResponseEntity(HttpStatus.OK);
  }


}
