package com.ycicic.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycicic.common.core.vo.TreeSelect;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.system.entity.SysMenu;
import com.ycicic.system.enums.WhetherEnum;
import com.ycicic.system.mapper.SysMenuMapper;
import com.ycicic.system.param.SysMenuQueryParam;
import com.ycicic.system.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ycicic
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> queryMenuListByUser(Long userId, SysMenuQueryParam param) {
        List<SysMenu> menuList = queryByUser(userId, param);

        return getChildPerms(menuList, 0);
    }

    @Override
    public Set<String> selectMenuPermsByUser(Long userId) {
        List<String> perms = baseMapper.selectMenuPermsByUser(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysMenu> queryMenuTreeByUser(Long userId) {
        List<SysMenu> menus;

        if (SecurityUtils.isAdmin(userId)) {
            menus = baseMapper.selectMenuTreeAll();
        } else {
            menus = baseMapper.selectMenuTreeByUser(userId);
        }
        return getChildPerms(menus, 0);
    }

    @Override
    public List<SysMenu> queryByUser(Long userId, SysMenuQueryParam param) {
        List<SysMenu> menuList;

        String menuName = param.getMenuName();
        WhetherEnum status = param.getStatus();

        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("1", "1");

        LambdaQueryWrapper<SysMenu> lambda = wrapper.lambda();
        lambda.like(Objects.nonNull(menuName), SysMenu::getMenuName, menuName);
        lambda.eq(Objects.nonNull(status), SysMenu::getStatus, status);

        if (SecurityUtils.isAdmin(userId)) {
            menuList = baseMapper.selectMenuList(lambda);
        } else {
            menuList = baseMapper.selectMenuListByUser(userId, lambda);
        }
        return menuList;
    }

    @Override
    public List<TreeSelect> buildTree(List<SysMenu> menuList) {
        return menuList.stream().map(this::buildTree).collect(Collectors.toList());
    }

    @Override
    public List<Long> queryIdListByRoleId(Long roleId) {
        return baseMapper.queryIdListByRoleId(roleId);
    }

    @Override
    public SysMenu getByName(String menuName) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getMenuName, menuName);
        wrapper.last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public List<SysMenu> queryByParentId(Long id) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, id);
        return list(wrapper);
    }

    private TreeSelect buildTree(SysMenu menu) {
        TreeSelect treeSelect = new TreeSelect();
        treeSelect.setId(menu.getId());
        treeSelect.setLabel(menu.getMenuName());
        List<SysMenu> children = menu.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            treeSelect.setChildren(children.stream().map(this::buildTree).collect(Collectors.toList()));
        }
        return treeSelect;
    }

    private List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tList = new ArrayList<>();
        for (SysMenu n : list) {
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tList.add(n);
            }
        }
        return tList;
    }

    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }


}
