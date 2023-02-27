package com.ycicic.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycicic.system.entity.SysRole;

import java.util.Set;

/**
 * @author ycicic
 */
public interface SysRoleService extends IService<SysRole> {

    Set<String> selectRolePermissionByUser(Long userId);

}
