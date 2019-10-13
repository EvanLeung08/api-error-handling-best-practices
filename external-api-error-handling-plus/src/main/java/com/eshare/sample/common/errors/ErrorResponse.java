package com.eshare.sample.common.errors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

/**
 * An immutable data structure representing HTTP error response bodies. JSON representation of this
 * class would be something like the following:
 * <pre>
 *     {
 *         "status_code": 404,
 *         "reason_phrase": "Not Found",
 *         "errors": [
 *             {"code": "EQ001", "message": "some, hopefully localized, error message"},
 *             {"code": "EQ002", "message": "yet another message"}
 *         ]
 *     }
 * </pre>
 *
 * @author Evan Liang
 */
@Data
@JsonInclude
@JsonAutoDetect(fieldVisibility = ANY)
public class ErrorResponse implements Serializable {

  /**
   * TraceId of each request, used for troubleshooting, e.g. 15cea764-8865-418d-b05f-1c395f6a4d96
   */
  @JSONField(name = "traceId")
  private String traceId;

  /**
   * The 4xx or 5xx status code for error cases, e.g. 404
   */
  @JSONField(name = "errorCode")
  private final int errorCode;

  /**
   * The HTTP reason phrase corresponding the {@linkplain #errorCode}, e.g. Not Found
   *
   * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html">Status Code and Reason
   * Phrase</a>
   */
  @JSONField(name = "errorMessage")
  private final String errorMessage;

  /**
   * List of application-level error code and message combinations. Using these errors we provide
   * more information about the actual error
   */
  @JSONField(name = "errorDetails")
  private final List<ApiError> errorStacks;

  /**
   * Construct a valid instance of the {@linkplain ErrorResponse}
   *
   * @throws IllegalArgumentException If one of passed parameters is null or invalid
   */
  private ErrorResponse(int errorCode, String errorMessage, String traceId,
      List<ApiError> errorStacks) {
    if (errorCode < 400 || errorCode > 600) {
      throw new IllegalArgumentException("Error Status codes should be between 400 and 599");
    }

    if (errorMessage == null || errorMessage.trim().isEmpty()) {
      throw new IllegalArgumentException("HTTP Response reason phrase can't be null or blank");
    }

    if (errorStacks == null || errorStacks.isEmpty()) {
      throw new IllegalArgumentException("Errors list can't be null or empty");
    }

    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorStacks = errorStacks;
    this.traceId = traceId;
  }

  /**
   * Static factory method to create a {@linkplain ErrorResponse} with multiple {@linkplain
   * ApiError}s. The canonical use case of this factory method is when we're handling validation
   * exceptions, since we may have multiple validation errorDetails.
   *
   * @param traceId the {@linkplain traceId}  record traceId for each request
   * @param status The {@linkplain HttpStatus} encapsulating both HTTP status code and it's reason
   * phrase
   * @param errorDetails List of {@linkplain ApiError}s for each application-level error.
   * @return An instance of {@linkplain ErrorResponse} with multiple {@linkplain ApiError}s
   */
  public static ErrorResponse ofErrors(String traceId, HttpStatus status,
      List<ApiError> errorDetails) {
    return new ErrorResponse(status.value(), status.getReasonPhrase(), traceId, errorDetails);
  }

  /**
   * Static factory method to create a {@linkplain ErrorResponse} with a single {@linkplain
   * ApiError}. The canonical use case for this method is when we trying to create {@linkplain
   * ErrorResponse}es for regular non-validation exceptions.
   *
   * @param traceId the {@linkplain traceId}  record traceId for each request
   * @param status The {@linkplain HttpStatus} encapsulating both status code and reason phrase
   * @param error The {@linkplain ApiError} encapsulating application-level error code and message
   * @return An instance of {@linkplain ErrorResponse} with just one {@linkplain ApiError}
   */
  public static ErrorResponse of(String traceId, HttpStatus status, ApiError error) {
    return ofErrors(traceId, status, Collections.singletonList(error));
  }

  /**
   * An immutable data structure representing each application-level error. JSON representation of
   * this class would be something like the following:
   * <pre>
   *     {"code": "geek-1", "message": "some error"}
   * </pre>
   *
   * @author Evan Liang
   */
  @Builder
  @Data
  @JsonAutoDetect(fieldVisibility = ANY)
  public static class ApiError {

    /**
     * The error code
     */
    private final String code;

    /**
     * Possibly localized error message
     */
    private final String message;

    public ApiError(String code, String message) {
      this.code = code;
      this.message = message;
    }
  }
}