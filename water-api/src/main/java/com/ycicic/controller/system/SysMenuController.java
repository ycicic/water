package com.ycicic.controller.system;

import com.ycicic.common.constants.Constants;
import com.ycicic.common.core.vo.Response;
import com.ycicic.common.core.vo.TreeSelect;
import com.ycicic.common.exception.ServiceException;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.system.entity.SysMenu;
import com.ycicic.system.enums.WhetherEnum;
import com.ycicic.system.param.SysMenuQueryParam;
import com.ycicic.system.param.SysMenuSaveParam;
import com.ycicic.system.service.SysMenuService;
import com.ycicic.system.service.SysRoleService;
import com.ycicic.system.vo.SysMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ycicic
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    private SysMenuService menuService;

    private SysRoleService roleService;

    @Autowired
    public void setMenuService(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setRoleService(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("获取菜单列表（有权限的）")
    @GetMapping("/query")
    public Response<List<SysMenuVo>> query(SysMenuQueryParam param) {
        Long loginUserId = SecurityUtils.getLoginUserId();
        List<SysMenu> menuList = menuService.queryByUser(loginUserId, param);
        List<SysMenuVo> menuVoList = menuList.stream().map(SysMenuVo::getBy).collect(Collectors.toList());
        return Response.success(menuVoList);
    }

    @ApiOperation("获取菜单详情")
    @GetMapping("/{id}")
    public Response<SysMenuVo> info(@PathVariable("id") Long id) {
        SysMenu byId = menuService.getById(id);
        return Response.success(SysMenuVo.getBy(byId));
    }

    @ApiOperation("保存菜单")
    @PostMapping("/save")
    public Response<?> save(@RequestBody SysMenuSaveParam param) {
        Long id = param.getId();
        String menuName = param.getMenuName();

        WhetherEnum isFrame = param.getIsFrame();
        String path = param.getPath();
        if (WhetherEnum.YES.equals(isFrame) && StringUtils.startsWithAny(path, Constants.HTTP, Constants.HTTPS)) {
            throw new ServiceException("保存失败，地址必须以http(s)://开头");
        }
        if (param.getParentId().equals(id)) {
            throw new ServiceException("保存失败，上级菜单不能选择自己");
        }
        SysMenu menuByName = menuService.getByName(menuName);
        if (Objects.nonNull(menuByName) && !menuByName.getId().equals(id)) {
            throw new ServiceException("保存失败，菜单名称已存在");
        }

        SysMenu menu = param.toEntity();
        if (Objects.isNull(id)) {
            menuService.save(menu);
        } else {
            menuService.updateById(menu);
        }

        return Response.success();
    }

    @ApiOperation("获取有权限的树状菜单")
    @GetMapping("/treeMyMenu")
    public Response<List<TreeSelect>> treeByUserId() {
        Long userId = SecurityUtils.getLoginUserId();
        List<SysMenu> menuList = menuService.queryMenuListByUser(userId, new SysMenuQueryParam());
        List<TreeSelect> menuTree = menuService.buildTree(menuList);
        return Response.success(menuTree);
    }

    @ApiOperation("通过角色ID获取菜单ID列表")
    @GetMapping("/queryIdByRoleId/{roleId}")
    public Response<List<Long>> queryIdByRoleId(@PathVariable("roleId") Long roleId) {
        List<Long> ids = menuService.queryIdListByRoleId(roleId);
        return Response.success(ids);
    }

    @ApiOperation("删除菜单")
    @PostMapping("/remove/{id}")
    public Response<?> remove(@PathVariable("id") Long id) {
        List<SysMenu> childList = menuService.queryByParentId(id);
        if (!CollectionUtils.isEmpty(childList)) {
            throw new ServiceException("存在子菜单，不允许删除");
        }
        Long roleCount = roleService.countByMenuId(id);
        if (roleCount > 0) {
            throw new ServiceException("菜单已分配，不允许删除");
        }

        menuService.removeById(id);
        return Response.success();
    }

}
