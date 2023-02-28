package com.ycicic.controller.system;

import cn.hutool.core.text.StrFormatter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ycicic.common.core.domain.BaseLoginUser;
import com.ycicic.common.core.vo.Response;
import com.ycicic.common.exception.ServiceException;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.framework.web.SysPermissionService;
import com.ycicic.framework.web.TokenService;
import com.ycicic.system.domain.SysLoginUser;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.param.SysRolePageParam;
import com.ycicic.system.param.SysRoleSaveParam;
import com.ycicic.system.service.SysRoleService;
import com.ycicic.system.service.SysUserService;
import com.ycicic.system.vo.SysRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ApiOperation("角色列表-分页查询")
    public Response<IPage<SysRoleVo>> page(SysRolePageParam param) {
        IPage<SysRole> rolePage = roleService.page(param);
        IPage<SysRoleVo> roleVoPage = rolePage.convert(SysRoleVo::getBy);
        return Response.success(roleVoPage);
    }

    @GetMapping("{id}")
    @ApiOperation("角色-详情")
    public Response<SysRoleVo> info(@PathVariable("id") Long id) {
        SysRole role = roleService.getById(id);
        SysRoleVo roleVo = SysRoleVo.getBy(role);
        return Response.success(roleVo);
    }

    @PostMapping("save")
    @ApiOperation("角色-保存")
    public Response<?> save(@RequestBody SysRoleSaveParam param) {
        Long id = param.getId();
        String roleName = param.getRoleName();

        checkAdmin(id);

        SysRole byRoleName = roleService.getByRoleName(roleName);
        if (Objects.nonNull(byRoleName) && !byRoleName.getId().equals(id)) {
            throw new ServiceException("角色名称已存在");
        }

        SysRole role = param.toEntity();
        if (Objects.nonNull(id)) {
            roleService.updateById(role);
            SysLoginUser loginUser = (SysLoginUser) SecurityUtils.getLoginUser();

            if (!SecurityUtils.isAdmin(loginUser.getUserId())) {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                tokenService.setLoginUser(loginUser);
            }
        } else {
            roleService.save(role);
        }

        return Response.success();
    }

    @PostMapping("/remove/{ids}")
    @ApiOperation("角色-批量删除")
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

    private void checkAdmin(Long id) {
        if (Objects.nonNull(id) && SecurityUtils.isAdmin(id)) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

}
