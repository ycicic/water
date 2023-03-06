package com.ycicic.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.enums.WhetherEnum;
import com.ycicic.system.mapper.SysRoleMapper;
import com.ycicic.system.param.SysRolePageParam;
import com.ycicic.system.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author ycicic
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public IPage<SysRole> page(SysRolePageParam param) {
        String roleName = param.getRoleName();
        WhetherEnum status = param.getStatus();
        LocalDate beginCreate = param.getBeginCreate();
        LocalDate endCreate = param.getEndCreate();

        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(roleName), SysRole::getRoleName, roleName);
        wrapper.eq(Objects.nonNull(status), SysRole::getStatus, status);

        wrapper.orderByAsc(SysRole::getRoleSort);

        if (Objects.nonNull(beginCreate)) {
            wrapper.ge(SysRole::getCreateTime, LocalDateTime.of(beginCreate, LocalTime.MIN));
        }
        if (Objects.nonNull(endCreate)) {
            wrapper.le(SysRole::getCreateTime, LocalDateTime.of(endCreate, LocalTime.MAX));
        }

        return page(param.getPage(SysRole.class), wrapper);
    }

    @Override
    public List<SysRole> queryByUserId(Long userId) {
        return baseMapper.selectRolePermissionByUser(userId);
    }

    @Override
    public SysRole getByRoleName(String roleName) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName, roleName);
        wrapper.last("limit 1");
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateRoleMenu(Long roleId, List<Long> menuIds) {
        baseMapper.removeRoleMenu(roleId);
        saveRoleMenu(roleId, menuIds);
    }

    @Override
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return;
        }
        baseMapper.saveRoleMenu(roleId, menuIds);
    }

    @Override
    public void changeStatus(Long id, WhetherEnum status) {
        SysRole role = getById(id);
        role.setStatus(status);
        updateById(role);
    }

    @Override
    public void cancelAuthUserBatch(Long roleId, List<Long> userIds) {
        baseMapper.cancelAuthUserBatch(roleId, userIds);
    }

    @Override
    public void authUserBatch(Long roleId, List<Long> userIds) {
        baseMapper.authUserBatch(roleId, userIds);
    }

    @Override
    public Long countByMenuId(Long menuId) {
        return baseMapper.countByMenuId(menuId);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        baseMapper.deleteRoleMenu(idList);
        return super.removeByIds(idList);
    }
}
