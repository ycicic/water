package com.ycicic.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 路由配置信息
 *
 * @author ycicic
 */
@Data
@ApiModel(description = "路由信息")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Router {
    /**
     * 路由名字
     */
    @ApiModelProperty("路由名字")
    private String name;

    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    @ApiModelProperty("是否隐藏路由")
    private boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    @ApiModelProperty("重定向地址")
    private String redirect;

    /**
     * 组件地址
     */
    @ApiModelProperty("组件地址")
    private String component;

    /**
     * 路由参数：如 {"id": 1, "name": "ry"}
     */
    @ApiModelProperty("路由参数")
    private String query;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    @ApiModelProperty(hidden = true)
    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    @ApiModelProperty("其他元素")
    private Meta meta;

    /**
     * 子路由
     */
    @ApiModelProperty("子路由")
    private List<Router> children;
}
