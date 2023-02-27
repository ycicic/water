package com.ycicic.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycicic
 */
@Data
@AllArgsConstructor
@ApiModel("验证码图片")
public class CaptchaImage implements Serializable {

    private static final long serialVersionUID = -5344415323579994490L;

    @ApiModelProperty("uuid")
    private String uuid;

    @ApiModelProperty("Base64图片")
    private String img;

}
