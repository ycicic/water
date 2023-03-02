package com.ycicic.controller.system;

import cn.hutool.core.text.StrFormatter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ycicic.common.core.vo.Response;
import com.ycicic.common.exception.ServiceException;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.framework.web.SysPermissionService;
import com.ycicic.framework.web.TokenService;
import com.ycicic.system.domain.SysLoginUser;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.param.*;
import com.ycicic.system.service.SysRoleService;
import com.ycicic.system.service.SysUserService;
import com.ycicic.system.vo.SysRoleVo;
import com.ycicic.system.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ycicic
 */
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    private SysRoleService roleService;

    private SysPermissionService permissionService;

    private TokenService tokenService;

    private SysUserService userService;

    @Autowired
    public void setRoleService(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(SysPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setUserService(SysUserService userService) {
        this.userService = userService;
    }

    @GetMapping("page")
    @ApiOperation("分页查询角色列表")
    public Response<IPage<SysRoleVo>> page(SysRolePageParam param) {
        IPage<SysRole> rolePage = roleService.page(param);
        IPage<SysRoleVo> roleVoPage = rolePage.convert(SysRoleVo::getBy);
        return Response.success(roleVoPage);
    }

    @GetMapping("{id}")
    @ApiOperation("查询角色详情")
    public Response<SysRoleVo> info(@PathVariable("id") Long id) {
        SysRole role = roleService.getById(id);
        SysRoleVo roleVo = SysRoleVo.getBy(role);
        return Response.success(roleVo);
    }

    @PostMapping("save")
    @ApiOperation("保存角色")
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<?> save(@RequestBody SysRoleSaveParam param) {
        Long id = param.getId();
        String roleName = param.getRoleName();

        checkAdmin(id);

        SysRole byRoleName = roleService.getByRoleName(roleName);
        if (Objects.nonNull(byRoleName) && !byRoleName.getId().equals(id)) {
            throw new ServiceException("角色名称已存在");
        }

        SysRole role = param.toEntity();
        List<Long> menuIds = param.getMenuIds();
        if (Objects.nonNull(id)) {
            roleService.updateById(role);
            roleService.updateRoleMenu(role.getId(), menuIds);
            SysLoginUser loginUser = (SysLoginUser) SecurityUtils.getLoginUser();

            if (!SecurityUtils.isAdmin(loginUser.getUserId())) {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                tokenService.setLoginUser(loginUser);
            }
        } else {
            roleService.save(role);
            roleService.saveRoleMenu(role.getId(), menuIds);
        }
        return Response.success();
    }

    @PostMapping("/changeStatus")
    @ApiOperation("修改角色状态")
    public Response<?> changeStatus(@RequestBody SysRoleSaveParam param) {
        checkAdmin(param.getId());
        roleService.changeStatus(param.getId(), param.getStatus());
        return Response.success();
    }

    @PostMapping("/remove/{ids}")
    @ApiOperation("批量删除角色")
    public Response<?> remove(@PathVariable("ids") List<Long> ids) {
        for (Long roleId : ids) {
            checkAdmin(roleId);
            Long userCount = userService.countByRoleId(roleId);
            if (userCount > 0) {
                SysRole role = roleService.getById(roleId);
                throw new ServiceException(StrFormatter.format("{} 已分配，不能删除", role.getRoleName()));
            }
        }
        roleService.removeByIds(ids);
        return Response.success();
    }

    @ApiOperation("查询已分配角色用户列表")
    @GetMapping("/auth/allocated/page")
    public Response<IPage<SysUserVo>> pageAllocated(AllocatedUserPageParam param) {
        IPage<SysUser> userPage = userService.pageAllocated(param);
        IPage<SysUserVo> userVoPage = userPage.convert(SysUserVo::getBy);
        return Response.success(userVoPage);
    }

    @ApiOperation("查询未分配角色用户列表")
    @GetMapping("/auth/unallocated/page")
    public Response<IPage<SysUserVo>> pageUnallocated(AllocatedUserPageParam param) {
        IPage<SysUser> userPage = userService.pageUnallocated(param);
        IPage<SysUserVo> userVoPage = userPage.convert(SysUserVo::getBy);
        return Response.success(userVoPage);
    }

    @ApiOperation("批量取消用户授权角色")
    @PostMapping("/auth/cancel")
    public Response<?> cancelAuthUser(@RequestBody AuthUserSaveParam param) {
        roleService.cancelAuthUserBatch(param.getRoleId(), param.getUserIds());
        return Response.success();
    }

    @ApiOperation("将角色授权给用户")
    @PostMapping("/auth/user")
    public Response<?> saveAuthRole(@RequestBody AuthUserSaveParam param) {
        roleService.authUserBatch(param.getRoleId(), param.getUserIds());
        return Response.success();
    }

    private void checkAdmin(Long id) {
        if (Objects.nonNull(id) && SecurityUtils.isAdmin(id)) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

}
