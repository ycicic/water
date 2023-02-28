package com.ycicic.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.param.SysRolePageParam;

import java.util.List;
import java.util.Set;

/**
 * @author ycicic
 */
public interface SysRoleService extends IService<SysRole> {

    IPage<SysRole> page(SysRolePageParam param);

    List<SysRole> queryByUserId(Long userId);

    SysRole getByRoleName(String roleName);
}
