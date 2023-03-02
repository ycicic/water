
package com.ycicic.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.enums.WhetherEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author ycicic
 */
@Data
@ApiModel(description = "角色信息-展示对象")
public class SysRoleVo {

    @ApiModelProperty("角色ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("排序")
    private Integer roleSort;

    @ApiModelProperty("状态")
    private WhetherEnum status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public static SysRoleVo getBy(SysRole role) {
        SysRoleVo roleVo = new SysRoleVo();
        BeanUtils.copyProperties(role, roleVo);
        return roleVo;
    }

}
