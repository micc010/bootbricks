package com.github.rogerli.utils;

import com.google.common.collect.ImmutableMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

public class BindErrorsUtils {

    /**
     * 用户检验实体合法性的辅助方法, 自动向Map封装错误信息
     *
     * @param result Spring MVC中与@Valid成对出现的BindingResult, 用于绑定错误信息
     * @return
     */
    public static Optional<ImmutableMap<String, Object>> bindErrors(BindingResult result) {
        ImmutableMap<String, Object> map = null;
        if (result.getErrorCount() > 0) {
            final ImmutableMap.Builder<String, Object> errorBuilder = ImmutableMap.builder();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorBuilder.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            map = errorBuilder.build();
        }
        return Optional.ofNullable(map);
    }
}
