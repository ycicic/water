package com.ycicic.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.enums.UserStatusEnum;
import com.ycicic.system.param.SysUserPageParam;

/**
 * @author ycicic
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUserName(String userName);

    IPage<SysUser> page(SysUserPageParam param);

    void changeStatus(Long userId, UserStatusEnum status);

    void resetPwd(Long userId, String password);
}
