package com.ycicic.system.param;

import io.swagger.annotations.ApiModel;
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

    private Long userId;

    private List<Long> roleIds;

}
