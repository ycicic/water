package com.ycicic.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ycicic.common.core.entity.BaseEntity;
import com.ycicic.system.enums.MenuTypeEnum;
import com.ycicic.system.enums.WhetherEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ycicic
 */
@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = -721475714611673655L;

    /**
     * 菜单ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链
     */
    private WhetherEnum isFrame;

    /**
     * 是否缓存
     */
    private WhetherEnum isCache;

    /**
     * 类型
     */
    private MenuTypeEnum menuType;

    /**
     * 是否可见
     */
    private WhetherEnum visible;

    /**
     * 是否可用
     */
    private WhetherEnum status;

    /**
     * 权限字符串
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    private String remark;

    @TableField(exist = false)
    private List<SysMenu> children;

}
