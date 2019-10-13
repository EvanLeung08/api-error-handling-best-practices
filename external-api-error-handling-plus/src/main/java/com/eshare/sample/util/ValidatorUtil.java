package com.eshare.sample.util;

import com.eshare.sample.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import org.springframework.util.ObjectUtils;

/**
 * Created by liangyh on 2019/9/22. Email:10856214@163.com
 */
public class ValidatorUtil {

  public static <T> T checkBeanParamValidate(@NotNull T t) {

    List<String> errorDetails = new ArrayList<>();
    Set<ConstraintViolation<T>> constraintViolationSet;
    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    Validator validator = vf.getValidator();
    constraintViolationSet = validator.validate(t);
    if (!ObjectUtils.isEmpty(constraintViolationSet)) {
      for (ConstraintViolation<T> constraint : constraintViolationSet) {
        errorDetails.add(
            constraint.getMessage() + ",invalid value: " + constraint.getInvalidValue() + ";");
      }
    }
    //Set error message
    if (!errorDetails.isEmpty()) {
      throw new ValidationException();
    }
    return t;
  }

}
