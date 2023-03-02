package com.ycicic.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.enums.UserStatusEnum;
import com.ycicic.system.param.AllocatedUserPageParam;
import com.ycicic.system.param.SysUserPageParam;

import java.util.List;

/**
 * @author ycicic
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUserName(String userName);

    IPage<SysUser> page(SysUserPageParam param);

    void changeStatus(Long userId, UserStatusEnum status);

    void resetPwd(Long userId, String password);

    Long countByRoleId(Long roleId);

    void reAuthRole(Long userId, List<Long> roleIds);

    void authRole(Long userId, List<Long> roleIds);

    IPage<SysUser> pageAllocated(AllocatedUserPageParam param);

    IPage<SysUser> pageUnallocated(AllocatedUserPageParam param);
}
