package com.g.miss.accountant.util;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.Iterator;
import java.util.Set;

/**
 * @author G
 * @description 驗證工具
 * @date 2023/6/17 5:44 PM
 */
public class ValidationUtils {
    public static void validate(@Valid Object user) {
        Set<ConstraintViolation<@Valid Object>> validateSet = Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(user);
        if (!CollectionUtils.isEmpty(validateSet)) {
            // 只顯示一個錯誤
            Iterator<ConstraintViolation<@Valid Object>> it = validateSet.iterator();
//            顯示全部錯誤
//            String messages = validateSet.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .reduce((m1, m2) -> m1 + ";" + m2)
//                    .orElse("參數輸入有誤！");

            throw new IllegalArgumentException(it.next().getMessage());
        }
    }
}
