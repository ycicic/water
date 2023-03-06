package com.ycicic.system.param;

import com.ycicic.system.enums.WhetherEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycicic
 */
@Data
@ApiModel(description = "菜单列表查询参数")
public class SysMenuQueryParam {

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单状态")
    private WhetherEnum status;

}
