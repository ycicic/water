package com.ycicic.controller.system;

import com.ycicic.common.core.param.LoginParam;
import com.ycicic.common.core.vo.Response;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.framework.web.SysLoginService;
import com.ycicic.framework.web.SysPermissionService;
import com.ycicic.system.domain.SysLoginUser;
import com.ycicic.system.entity.SysMenu;
import com.ycicic.system.service.RouterService;
import com.ycicic.system.service.SysMenuService;
import com.ycicic.system.vo.Router;
import com.ycicic.system.vo.SysUserDetail;
import com.ycicic.system.vo.SysUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author ycicic
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("/system")
public class SysLoginController {

    private SysLoginService loginService;

    private SysPermissionService permissionService;

    private SysMenuService menuService;

    private RouterService routerService;

    @Autowired
    public void setLoginService(SysLoginService loginService) {
        this.loginService = loginService;
    }

    @Autowired
    public void setPermissionService(SysPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setMenuService(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setRouterService(RouterService routerService) {
        this.routerService = routerService;
    }

    @ApiOperation("账号密码登录")
    @PostMapping("/login")
    public Response<?> login(@RequestBody LoginParam loginParam) {
        String token = loginService.login(loginParam.getUsername(), loginParam.getPassword(), loginParam.getCode(), loginParam.getUuid());
        return Response.success(token);
    }

    @ApiOperation("获取系统用户信息")
    @GetMapping("/userInfo")
    public Response<SysUserDetail> userInfo() {
        SysLoginUser loginUser = (SysLoginUser) SecurityUtils.getLoginUser();
        SysUserInfo user = loginUser.getUser();
        Set<String> permissions = permissionService.getMenuPermission(user);
        return Response.success(SysUserDetail.builder().user(user).permissions(permissions).build());
    }

    @ApiOperation("获取路由信息")
    @GetMapping("/routers")
    public Response<List<Router>> routers() {
        Long userId = SecurityUtils.getLoginUserId();
        List<SysMenu> menus = menuService.queryMenuTreeByUser(userId);
        List<Router> routerList = routerService.buildRouter(menus);
        return Response.success(routerList);
    }

}
