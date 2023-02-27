package com.ycicic.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.enums.GenderEnum;
import com.ycicic.system.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author ycicic
 */
@Data
@ApiModel("用户信息-展示对象")
public class SysUserVo {

    @ApiModelProperty("用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("用户账号")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private GenderEnum gender;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("帐号状态")
    private UserStatusEnum status;

    @ApiModelProperty("用户说明")
    private String remark;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public static SysUserVo getBy(SysUser user) {
        SysUserVo userVo = new SysUserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

}
