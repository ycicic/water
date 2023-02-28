package com.ycicic.common.core.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录对象
 *
 * @author ycicic
 */
@Data
@ApiModel(description = "登录参数")
public class LoginParam {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("图形验证码标识")
    private String uuid;

    @ApiModelProperty("手机号")
    private String mobile;

}
