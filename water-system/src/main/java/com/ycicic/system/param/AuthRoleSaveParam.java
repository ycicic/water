package com.ycicic.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 为用用户分配角色参数
 *
 * @author ycicic
 */
@Data
@ApiModel(description = "为用用户分配角色参数")
public class AuthRoleSaveParam {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色列表")
    private List<Long> roleIds;

}
