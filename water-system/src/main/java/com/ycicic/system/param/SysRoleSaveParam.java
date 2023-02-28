package com.ycicic.system.param;

import com.ycicic.common.core.param.EntityParam;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.enums.WhetherEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author ycicic
 */
@Data
@ApiModel(description = "用户信息-保存参数")
public class SysRoleSaveParam implements EntityParam<SysRole> {

    @ApiModelProperty("角色ID")
    private Long id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("排序")
    private String roleSort;

    @ApiModelProperty("状态")
    private WhetherEnum status;

    @ApiModelProperty("备注")
    private String remark;

    @Override
    public SysRole toEntity() {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(this, role);
        return role;
    }
}
