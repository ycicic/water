package com.ycicic.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ycicic.system.entity.SysMenu;
import com.ycicic.system.enums.MenuTypeEnum;
import com.ycicic.system.enums.WhetherEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author ycicic
 */
@Data
@ApiModel(description = "菜单信息-展示对象")
public class SysMenuVo {

    @ApiModelProperty("菜单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("父菜单ID")
    @JsonSerialize(using = ToStringSerializer.class)
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

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public static SysMenuVo getBy(SysMenu menu) {
        SysMenuVo menuVo = new SysMenuVo();
        BeanUtils.copyProperties(menu, menuVo);
        return menuVo;
    }

}
