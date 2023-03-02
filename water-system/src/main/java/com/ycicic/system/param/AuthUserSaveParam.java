package com.ycicic.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 为用角色授权/取消授权用户参数
 *
 * @author ycicic
 */
@Data
@ApiModel(description = "为用角色授权/取消授权用户参数")
public class AuthUserSaveParam {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户ID列表")
    private List<Long> userIds;

}
