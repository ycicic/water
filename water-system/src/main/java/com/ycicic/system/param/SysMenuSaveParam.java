package com.ycicic.system.param;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ycicic.common.core.param.EntityParam;
import com.ycicic.system.entity.SysMenu;
import com.ycicic.system.enums.MenuTypeEnum;
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
public class SysMenuSaveParam implements EntityParam<SysMenu> {

    @ApiModelProperty("菜单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("父菜单ID")
    private Long parentId;

    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("路由参数")
    private String query;

    @ApiModelProperty("是否为外链")
    private WhetherEnum isFrame;

    @ApiModelProperty("是否缓存")
    private WhetherEnum isCache;

    @ApiModelProperty("类型")
    private MenuTypeEnum menuType;

    @ApiModelProperty("是否可见")
    private WhetherEnum visible;

    @ApiModelProperty("是否可用")
    private WhetherEnum status;

    @ApiModelProperty("权限字符串")
    private String perms;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("备注")
    private String remark;

    @Override
    public SysMenu toEntity() {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(this, menu);
        return menu;
    }
}
