package com.ycicic.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Select("<script>\n" +
            "select distinct u.id,\n" +
            "                u.user_name,\n" +
            "                u.nick_name,\n" +
            "                u.email,\n" +
            "                u.phone,\n" +
            "                u.gender,\n" +
            "                u.avatar,\n" +
            "                u.password,\n" +
            "                u.status,\n" +
            "                u.remark,\n" +
            "                u.create_by,\n" +
            "                u.create_time,\n" +
            "                u.update_by,\n" +
            "                u.update_time,\n" +
            "                u.deleted\n" +
            "from sys_user u\n" +
            "         left join sys_user_role ur on u.id = ur.user_id\n" +
            "         left join sys_role r on r.id = ur.role_id\n" +
            "where u.deleted = 0\n" +
            "   and r.id = #{roleId}\n" +
            "<if test=\"userName != null and userName != ''\">\n" +
            "   and u.user_name like concat('%', #{userName}, '%')\n" +
            "</if>\n" +
            "<if test=\"phone != null and phone != ''\">\n" +
            "   and u.phone like concat('%', #{phone}, '%')\n" +
            "</if>\n" +
            "</script>")
    IPage<SysUser> pageAllocated(Page<Object> objectPage, @Param("roleId") Long roleId, @Param("userName") String userName, @Param("phone") String phone);

    @Select("<script>\n" +
            "select distinct u.id,\n" +
            "                u.user_name,\n" +
            "                u.nick_name,\n" +
            "                u.email,\n" +
            "                u.phone,\n" +
            "                u.gender,\n" +
            "                u.avatar,\n" +
            "                u.password,\n" +
            "                u.status,\n" +
            "                u.remark,\n" +
            "                u.create_by,\n" +
            "                u.create_time,\n" +
            "                u.update_by,\n" +
            "                u.update_time,\n" +
            "                u.deleted\n" +
            "from sys_user u\n" +
            "         left join sys_user_role ur on u.id = ur.user_id\n" +
            "         left join sys_role r on r.id = ur.role_id\n" +
            "where u.deleted = 0\n" +
            "   and (r.id != #{roleId} or r.id is null)\n" +
            "<if test=\"userName != null and userName != ''\">\n" +
            "   and u.user_name like concat('%', #{userName}, '%')\n" +
            "</if>\n" +
            "<if test=\"phone != null and phone != ''\">\n" +
            "   and u.phone like concat('%', #{phone}, '%')\n" +
            "</if>\n" +
            "</script>")
    IPage<SysUser> pageUnallocated(Page<Object> objectPage, @Param("roleId") Long roleId, @Param("userName") String userName, @Param("phone") String phone);

}
