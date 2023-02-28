package com.ycicic.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ycicic.system.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author ycicic
 */
@Data
@ApiModel(description = "用户角色")
public class UserRole {

    @ApiModelProperty("角色ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("用户是否拥有该角色")
    private Boolean flag = false;

    public static UserRole getBy(SysRole sysRole) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(sysRole, userRole);
        return userRole;
    }

    public boolean isAdmin() {
        return id != null && 1L == id;
    }

}
