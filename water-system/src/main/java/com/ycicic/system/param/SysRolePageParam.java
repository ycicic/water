package com.ycicic.system.param;

import com.ycicic.common.core.param.PageParam;
import com.ycicic.system.enums.WhetherEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author ycicic
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "角色分页查询参数")
public class SysRolePageParam extends PageParam {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("状态")
    private WhetherEnum status;

    @ApiModelProperty("创建时间-起始")
    private LocalDate beginCreate;

    @ApiModelProperty("创建时间-结束")
    private LocalDate endCreate;

}
