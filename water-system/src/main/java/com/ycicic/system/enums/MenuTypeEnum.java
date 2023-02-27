package com.ycicic.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ycicic.common.core.enums.ApiEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ycicic
 */
@Getter
@AllArgsConstructor
@ApiEnum
public enum MenuTypeEnum {

    /**
     * 目录
     */
    M("M", "目录"),

    /**
     * 菜单
     */
    C("C", "菜单"),

    /**
     * 按钮
     */
    F("F", "按钮");

    @EnumValue
    @JsonValue
    private final String code;
    private final String value;
}
