package com.ycicic.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycicic.common.core.vo.TreeSelect;
import com.ycicic.system.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * @author ycicic
 */
public interface SysMenuService extends IService<SysMenu> {

    Set<String> selectMenuPermsByUser(Long userId);

    List<SysMenu> queryMenuTreeByUser(Long userId);

    List<SysMenu> queryMenuListByUser(Long userId);

    List<TreeSelect> buildTree(List<SysMenu> menuList);

    List<Long> queryIdListByRoleId(Long roleId);
}
