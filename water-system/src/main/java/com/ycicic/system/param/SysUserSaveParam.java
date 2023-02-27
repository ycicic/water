package com.ycicic.system.param;

import com.ycicic.common.core.param.EntityParam;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.enums.GenderEnum;
import com.ycicic.system.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author ycicic
 */
@Data
@ApiModel("用户信息-保存参数")
public class SysUserSaveParam implements EntityParam<SysUser> {

    @ApiModelProperty("用户ID")
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

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("帐号状态")
    private UserStatusEnum status;

    @ApiModelProperty("用户说明")
    private String remark;

    @Override
    public SysUser toEntity() {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
