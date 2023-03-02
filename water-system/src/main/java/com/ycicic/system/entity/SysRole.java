package com.ycicic.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ycicic.common.core.entity.BaseEntity;
import com.ycicic.system.enums.WhetherEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ycicic
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = -7972185379064776060L;

    /**
     * 角色ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色排序
     */
    private Integer roleSort;

    /**
     * 角色状态是否可用
     */
    private WhetherEnum status;

    private String remark;

}
