package com.ycicic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ycicic.common.core.vo.Response;
import com.ycicic.common.exception.ServiceException;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.param.SysUserPageParam;
import com.ycicic.system.param.SysUserSaveParam;
import com.ycicic.system.service.SysUserService;
import com.ycicic.system.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    public void setUserService(SysUserService userService) {
        this.userService = userService;
    }

    @GetMapping("page")
    @ApiOperation("用户列表-分页查询")
    public Response<IPage<SysUserVo>> page(SysUserPageParam param) {
        IPage<SysUser> userPage = userService.page(param);
        IPage<SysUserVo> voPage = userPage.convert(SysUserVo::getBy);
        return Response.success(voPage);
    }

    @GetMapping("/{id}")
    public Response<SysUserVo> info(@PathVariable("id") Long id) {
        SysUser user = userService.getById(id);
        SysUserVo userVo = SysUserVo.getBy(user);
        return Response.success(userVo);
    }

    @PostMapping("save")
    @ApiOperation("用户-保存")
    public Response<?> save(@RequestBody SysUserSaveParam param) {
        Long userId = param.getId();
        SysUser sysUser = param.toEntity();

        if (Objects.nonNull(userId)) {
            checkAdmin(userId);
            userService.updateById(sysUser);
        } else {
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

    private void checkAdmin(Long id) {
        if (Objects.nonNull(id) && SecurityUtils.isAdmin(id)) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

}
