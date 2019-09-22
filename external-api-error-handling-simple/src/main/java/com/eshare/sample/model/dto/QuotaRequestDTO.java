package com.eshare.sample.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

/**
 * Created by liangyh on 2019/9/22. Email:10856214@163.com
 */
@Data
@ToString
public class QuotaRequestDTO {

  /**
   * Customer account
   */
  @NotBlank(message = "customerId can not be blank.")
  private String customerId;
  /**
   * Total quota amount
   */
  @NotNull(message = "quotaLimit can not be blank.")
  private Integer quotaLimit;
  /**
   * Quoto balance
   */
  @NotNull(message = "quotaBalance can not be blank.")
  private Integer quotaBalance;

}
