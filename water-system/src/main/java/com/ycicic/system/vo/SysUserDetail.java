package com.ycicic.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author ycicic
 */
@Data
@Builder
@ApiModel("系统用户信息")
public class SysUserDetail {

    @ApiModelProperty("系统用户")
    private SysUserInfo user;

    @ApiModelProperty("角色集合")
    private Set<String> roles;

    @ApiModelProperty("权限集合")
    private Set<String> permissions;

}
