package com.eshare.sample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Quota response data
 *
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotaResponseDTO {

  /**
   * Customer account
   */
  private String customerId;
  /**
   * Total quota amount
   */
  private Integer quotaLimit;
  /**
   * Quoto balance
   */
  private Integer quotaBalance;

}
