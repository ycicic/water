package com.ycicic.framework.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Enum转换器
 *
 * @author ycicic
 */
public class EnumConverter implements ConverterFactory<String, Enum<?>> {
    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new KeyToEnum<>(targetType);
    }
}
