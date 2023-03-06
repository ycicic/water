package com.ycicic.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.enums.WhetherEnum;
import com.ycicic.system.param.SysRolePageParam;

import java.util.List;

/**
 * @author ycicic
 */
public interface SysRoleService extends IService<SysRole> {

    IPage<SysRole> page(SysRolePageParam param);

    List<SysRole> queryByUserId(Long userId);

    SysRole getByRoleName(String roleName);

    void updateRoleMenu(Long roleId, List<Long> menuIds);

    void saveRoleMenu(Long roleId, List<Long> menuIds);

    void changeStatus(Long id, WhetherEnum status);

    void cancelAuthUserBatch(Long roleId, List<Long> userIds);

    void authUserBatch(Long roleId, List<Long> userIds);

    Long countByMenuId(Long menuId);
}
