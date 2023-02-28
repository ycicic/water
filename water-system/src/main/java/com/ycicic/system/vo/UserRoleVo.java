package com.ycicic.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycicic
 */
@Data
@ApiModel(description = "用户角色展示对象")
public class UserRoleVo {

    @ApiModelProperty("用户信息")
    private SysUserVo user;

    @ApiModelProperty("角色集合")
    private List<UserRole> roles;

}
