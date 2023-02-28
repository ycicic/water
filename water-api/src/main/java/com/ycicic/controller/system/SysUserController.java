package com.ycicic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ycicic.common.core.vo.Response;
import com.ycicic.common.exception.ServiceException;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.param.AuthRoleSaveParam;
import com.ycicic.system.param.SysUserPageParam;
import com.ycicic.system.param.SysUserSaveParam;
import com.ycicic.system.service.SysRoleService;
import com.ycicic.system.service.SysUserService;
import com.ycicic.system.vo.SysUserVo;
import com.ycicic.system.vo.UserRole;
import com.ycicic.system.vo.UserRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ycicic
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private SysUserService userService;

    private SysRoleService roleService;

    @Autowired
    public void setUserService(SysUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/page")
    @ApiOperation("用户列表-分页查询")
    public Response<IPage<SysUserVo>> page(SysUserPageParam param) {
        IPage<SysUser> userPage = userService.page(param);
        IPage<SysUserVo> voPage = userPage.convert(SysUserVo::getBy);
        return Response.success(voPage);
    }

    @GetMapping("/{id}")
    @ApiOperation("用户-详情")
    public Response<SysUserVo> info(@PathVariable("id") Long id) {
        SysUser user = userService.getById(id);
        SysUserVo userVo = SysUserVo.getBy(user);
        return Response.success(userVo);
    }

    @PostMapping("/save")
    @ApiOperation("用户-保存")
    public Response<?> save(@RequestBody SysUserSaveParam param) {
        Long userId = param.getId();
        SysUser sysUser = param.toEntity();

        if (Objects.nonNull(userId)) {
            checkAdmin(userId);
            userService.updateById(sysUser);
        } else {
            SysUser byUserName = userService.getByUserName(sysUser.getUserName());
            if (Objects.nonNull(byUserName)) {
                throw new ServiceException("用户账号已存在");
            }

            String password = sysUser.getPassword();
            String encryptPassword = SecurityUtils.encryptPassword(password);
            sysUser.setPassword(encryptPassword);
            userService.save(sysUser);
        }

        return Response.success();
    }

    @PostMapping("/changeStatus")
    @ApiOperation("用户-修改状态")
    public Response<?> changeStatus(@RequestBody SysUserSaveParam param) {
        checkAdmin(param.getId());
        userService.changeStatus(param.getId(), param.getStatus());
        return Response.success();
    }

    @PostMapping("/resetPwd")
    @ApiOperation("用户-修改密码")
    public Response<?> resetPwd(@RequestBody SysUserSaveParam param) {
        try {
            checkAdmin(param.getId());
        } catch (ServiceException e) {
            if (!SecurityUtils.isAdmin()) {
                throw e;
            }
        }
        userService.resetPwd(param.getId(), SecurityUtils.encryptPassword(param.getPassword()));
        return Response.success();
    }

    @PostMapping("/remove/{ids}")
    @ApiOperation("用户-批量删除")
    public Response<?> remove(@PathVariable("ids") List<Long> ids) {
        Long loginUserId = SecurityUtils.getLoginUserId();

        if (ids.contains(loginUserId)) {
            throw new ServiceException("当前用户不能删除");
        }
        ids.forEach(this::checkAdmin);

        userService.removeByIds(ids);
        return Response.success();
    }

    @GetMapping("/authRole/{userId}")
    @ApiOperation("用户-查询绑定角色")
    public Response<UserRoleVo> getAuthRole(@PathVariable("userId") Long userId) {
        SysUser user = userService.getById(userId);
        List<UserRole> allRoles = roleService.list().stream().map(UserRole::getBy).collect(Collectors.toList());
        List<SysRole> userRoles = roleService.queryByUserId(userId);

        for (UserRole allRole : allRoles) {
            for (SysRole userRole : userRoles) {
                if (allRole.getId().equals(userRole.getId())) {
                    allRole.setFlag(true);
                }
            }
        }

        allRoles = SecurityUtils.isAdmin(userId) ? allRoles : allRoles.stream().filter(UserRole::isAdmin).collect(Collectors.toList());

        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setUser(SysUserVo.getBy(user));
        userRoleVo.setRoles(allRoles);

        return Response.success(userRoleVo);
    }

    @PostMapping("/authRole")
    public Response<?> saveAuthRole(@RequestBody AuthRoleSaveParam param) {
        userService.reBindRole(param.getUserId(),param.getRoleIds());
        return Response.success();
    }

    private void checkAdmin(Long id) {
        if (Objects.nonNull(id) && SecurityUtils.isAdmin(id)) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

}
