package com.ycicic.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ycicic.common.core.enums.ApiEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别 枚举
 *
 * @author ycicic
 */
@Getter
@AllArgsConstructor
@ApiEnum
public enum GenderEnum {

    /**
     * 未知
     */
    NONE(0, "未知"),
    /**
     * 男
     */
    MAN(1, "男"),
    /**
     * 女
     */
    WOMAN(2, "女");

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String value;

}
