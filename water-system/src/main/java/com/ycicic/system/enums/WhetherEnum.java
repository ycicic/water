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
public enum WhetherEnum {

    /**
     * 是
     */
    YES(0, "是"),
    /**
     * 否
     */
    NO(1, "否");

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String value;

}
