package com.ycicic.system.param;

import com.ycicic.common.core.param.PageParam;
import com.ycicic.system.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author ycicic
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户分页查询参数")
public class SysUserPageParam extends PageParam {

    @ApiModelProperty("用户账号")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("用户状态")
    private UserStatusEnum status;

    @ApiModelProperty("创建时间-起始")
    private LocalDate beginCreate;

    @ApiModelProperty("创建时间-结束")
    private LocalDate endCreate;

}
