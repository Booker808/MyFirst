package com.csjscm.core.framework.common.util;


import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author robin
 * @version v1.0.0
 * 描述：表单校验工具类
 * @since 2017/11/24 下午3:07
 */
public class BeanValidator {
    /**
     * 验证某个bean的参数
     *
     * @param object 被校验的参数
     * @throws ValidationException 如果参数校验不成功则抛出此异常
     */
    public static <T> void validate(T object) {
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

        for (ConstraintViolation<T> violation : constraintViolations) {
            if (StringUtils.isNotBlank(violation.getMessage())) {
                throw new ValidationException(violation.getMessage());
            }
        }

    }


}
