package com.ycicic.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ycicic.common.core.enums.ApiEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态 枚举
 *
 * @author ycicic
 */
@Getter
@AllArgsConstructor
@ApiEnum
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 停用
     */
    DEACTIVATE(1, "停用");

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String value;
}
