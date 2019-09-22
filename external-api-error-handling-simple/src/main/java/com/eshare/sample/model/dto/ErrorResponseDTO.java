package com.eshare.sample.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liangyh on 2019/9/21. Email:10856214@163.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDTO {

  private String traceId;
  private String errorCode;
  private String errorMessage;
  private List<String> errorDetails;

}
