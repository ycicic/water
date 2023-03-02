package com.ycicic.controller.system;

import com.ycicic.common.core.vo.Response;
import com.ycicic.common.core.vo.TreeSelect;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.system.entity.SysMenu;
import com.ycicic.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ycicic
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    private SysMenuService menuService;

    @Autowired
    public void setMenuService(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation("获取有权限的树状菜单")
    @GetMapping("/treeMyMenu")
    public Response<List<TreeSelect>> treeByUserId() {
        Long userId = SecurityUtils.getLoginUserId();
        List<SysMenu> menuList = menuService.queryMenuListByUser(userId);
        List<TreeSelect> menuTree = menuService.buildTree(menuList);
        return Response.success(menuTree);
    }

    @ApiOperation("通过角色ID获取菜单ID列表")
    @GetMapping("/queryIdByRoleId/{roleId}")
    public Response<List<Long>> queryIdByRoleId(@PathVariable("roleId") Long roleId) {
        List<Long> ids = menuService.queryIdListByRoleId(roleId);
        return Response.success(ids);
    }

}
