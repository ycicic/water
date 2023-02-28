package com.ycicic.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycicic.system.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ycicic
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select count(1) from sys_user_role where role_id = #{roleId}")
    Long countByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from sys_user_role where user_id = #{userId}")
    void deleteUserRoleByUserId(@Param("userId") Long userId);

    @Insert("<script>\n" +
            "insert into sys_user_role(user_id, role_id) values\n" +
            "<foreach item='roleId' index='index' collection='roleIds' separator=','>\n" +
            "   (#{userId},#{roleId})\n" +
            "</foreach>\n" +
            "</script>\n")
    void bindRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

}
