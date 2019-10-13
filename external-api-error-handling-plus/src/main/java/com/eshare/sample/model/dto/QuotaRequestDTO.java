package com.eshare.sample.model.dto;

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
  @NotBlank(message = "quota-1")
  private String customerId;
  /**
   * Total quota amount
   */
  @NotNull(message = "quota-2")
  private Integer quotaLimit;
  /**
   * Quoto balance
   */
  @NotNull(message = "quota-3")
  private Integer quotaBalance;

}
