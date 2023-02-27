package com.ycicic.framework.web;

import com.ycicic.system.service.SysMenuService;
import com.ycicic.system.service.SysRoleService;
import com.ycicic.system.vo.SysUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ycicic
 */
@Service
public class SysPermissionService {

    private SysMenuService sysMenuService;

    private SysRoleService sysRoleService;

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 获取角色权限
     *
     * @param user 用户信息
     * @return 角色权限
     */
    public Set<String> getRolePermission(SysUserInfo user) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(sysRoleService.selectRolePermissionByUser(user.getId()));
        }
        return roles;
    }

    /**
     * 获取菜单权限
     *
     * @param user 用户信息
     * @return 菜单信息
     */
    public Set<String> getMenuPermission(SysUserInfo user) {
        Long id = user.getId();
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            Set<String> permSet = sysMenuService.selectMenuPermsByUser(id);
            perms.addAll(permSet);
        }
        return perms;
    }

}
