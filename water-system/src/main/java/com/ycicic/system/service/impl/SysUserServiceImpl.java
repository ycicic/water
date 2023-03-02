package com.ycicic.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.enums.UserStatusEnum;
import com.ycicic.system.mapper.SysUserMapper;
import com.ycicic.system.param.AllocatedUserPageParam;
import com.ycicic.system.param.SysUserPageParam;
import com.ycicic.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * @author ycicic
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getByUserName(String userName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, userName);
        return getOne(wrapper);
    }

    @Override
    public IPage<SysUser> page(SysUserPageParam param) {
        String userName = param.getUserName();
        String nickName = param.getNickName();
        String phone = param.getPhone();
        UserStatusEnum status = param.getStatus();
        LocalDate beginCreate = param.getBeginCreate();
        LocalDate endCreate = param.getEndCreate();

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(userName), SysUser::getUserName, userName);
        wrapper.like(StringUtils.isNotEmpty(nickName), SysUser::getNickName, nickName);
        wrapper.eq(StringUtils.isNotEmpty(phone), SysUser::getPhone, phone);
        wrapper.eq(Objects.nonNull(status), SysUser::getStatus, status);

        if (Objects.nonNull(beginCreate)) {
            wrapper.ge(SysUser::getCreateTime, LocalDateTime.of(beginCreate, LocalTime.MIN));
        }
        if (Objects.nonNull(endCreate)) {
            wrapper.le(SysUser::getCreateTime, LocalDateTime.of(endCreate, LocalTime.MAX));
        }

        return page(param.getPage(SysUser.class), wrapper);
    }

    @Override
    public void changeStatus(Long userId, UserStatusEnum status) {
        SysUser byId = getById(userId);
        byId.setStatus(status);

        updateById(byId);
    }

    @Override
    public void resetPwd(Long userId, String password) {
        SysUser byId = getById(userId);
        byId.setPassword(password);

        updateById(byId);
    }

    @Override
    public Long countByRoleId(Long roleId) {
        return baseMapper.countByRoleId(roleId);
    }

    @Override
    public void reAuthRole(Long userId, List<Long> roleIds) {
        baseMapper.deleteUserRoleByUserId(userId);
        authRole(userId, roleIds);
    }

    @Override
    public void authRole(Long userId, List<Long> roleIds) {
        if (!CollectionUtils.isEmpty(roleIds)) {
            baseMapper.bindRole(userId, roleIds);
        }
    }

    @Override
    public IPage<SysUser> pageAllocated(AllocatedUserPageParam param) {
        return baseMapper.pageAllocated(param.getPage(), param.getRoleId(), param.getUserName(), param.getPhone());
    }

    @Override
    public IPage<SysUser> pageUnallocated(AllocatedUserPageParam param) {
        return baseMapper.pageUnallocated(param.getPage(), param.getRoleId(), param.getUserName(), param.getPhone());
    }

}
