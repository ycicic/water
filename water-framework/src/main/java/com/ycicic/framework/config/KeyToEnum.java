package com.ycicic.framework.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.core.convert.converter.Converter;

import java.lang.reflect.Field;

/**
 * @author ycicic
 */
public class KeyToEnum<T extends Enum> implements Converter<String, T> {

    private final Class<T> enumType;

    public KeyToEnum(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        try {
            //先通过name获取枚举
            return (T) Enum.valueOf(enumType, source);
        } catch (Exception e) {
            Field[] declaredFields = enumType.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                JsonValue[] annotationsByType = declaredField.getAnnotationsByType(JsonValue.class);
                if (annotationsByType.length > 0) {
                    String name = declaredField.getName();
                    Object convert = Convert.convert(declaredField.getType(), source);
                    return getEnumObj(enumType, name, convert);
                }
            }
        }
        return null;
    }

    /**
     * 通过反射的方式 获取枚举实例对象
     */
    private T getEnumObj(Class<T> clazz, String fieldName, Object source) {
        T[] enums = clazz.getEnumConstants();
        if (null != enums) {
            for (T e : enums) {
                Object fieldValue = ReflectUtil.getFieldValue(e, fieldName);
                if (fieldValue.equals(source)) {
                    return e;
                }
            }
        }
        return null;
    }
}
