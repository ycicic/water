package com.ycicic.system.param;

import com.ycicic.common.core.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ycicic
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "分配角色用户列表查询参数")
public class AllocatedUserPageParam extends PageParam {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户账号")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

}
