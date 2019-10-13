package com.eshare.sample.exception.mapping;

import com.eshare.sample.common.errors.ErrorCode;
import com.eshare.sample.common.errors.ExceptionToErrorCode;
import com.eshare.sample.constant.ApiErrorCodes;
import com.eshare.sample.exception.InvalidAccountException;
import com.eshare.sample.exception.InvalidAuthenticationException;
import com.eshare.sample.exception.ValidationException;
import org.springframework.stereotype.Component;

class QuotaApiExceptionMappers {

    @Component
    static class InvalidAccountExceptionToErrorCode implements ExceptionToErrorCode {
        @Override
        public boolean canHandle(Exception exception) {
            return exception instanceof InvalidAccountException;
        }

        @Override
        public ErrorCode toErrorCode(Exception exception) {
            return ApiErrorCodes.INVALID_ACCOUNT;
        }
    }

    @Component
    static class InvalidAuthenticationExceptionToErrorCode implements ExceptionToErrorCode {
        @Override
        public boolean canHandle(Exception exception) {
            return exception instanceof InvalidAuthenticationException;
        }

        @Override
        public ErrorCode toErrorCode(Exception exception) {
            return ApiErrorCodes.INVALID_AUTHENTICATION;
        }
    }

    @Component
    static class ValidationExceptionToErrorCode implements ExceptionToErrorCode {
        @Override
        public boolean canHandle(Exception exception) {
            return exception instanceof ValidationException;
        }

        @Override
        public ErrorCode toErrorCode(Exception exception) {
            return ApiErrorCodes.VALIDATION_ERROR;
        }
    }


}