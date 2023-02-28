package com.ycicic.system.vo;

import com.ycicic.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 路由显示信息
 *
 * @author ycicic
 */
@Data
@ApiModel(description = "路由显示信息")
public class Meta {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @ApiModelProperty("展示名")
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    @ApiModelProperty("路由图标")
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    @ApiModelProperty(hidden = true)
    private boolean noCache;

    /**
     * 内链地址（http(s)://开头）
     */
    @ApiModelProperty("内链地址")
    private String link;

    public Meta() {
    }

    public Meta(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public Meta(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public Meta(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public Meta(String title, String icon, boolean noCache, String link) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS)) {
            this.link = link;
        }
    }
}
