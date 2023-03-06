package com.ycicic.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycicic.system.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author ycicic
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select distinct r.*\n" +
            "from sys_role r\n" +
            "         left join sys_user_role ur on ur.role_id = r.id\n" +
            "         left join sys_user u on u.id = ur.user_id\n" +
            "where r.deleted = 0 and ur.user_id = #{userId}")
    List<SysRole> selectRolePermissionByUser(@Param("userId") Long userId);

    @Delete("<script>\n" +
            "delete from sys_role_menu where role_id in\n" +
            "<foreach collection='idList' item='roleId' open='(' separator=',' close=')'>\n" +
            "   #{roleId}\n" +
            "</foreach>\n" +
            "</script>")
    void deleteRoleMenu(@Param("idList") Collection<? extends Serializable> idList);

    @Insert("<script>\n" +
            "insert into sys_role_menu(role_id, menu_id) values\n" +
            "<foreach item='menuId' index='index' collection='menuIds' separator=','>\n" +
            "   (#{roleId},#{menuId})\n" +
            "</foreach>\n" +
            "</script>")
    void saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    @Delete("delete from sys_role_menu where role_id=#{roleId}")
    void removeRoleMenu(@Param("roleId") Long roleId);

    @Delete("<script>\n" +
            "delete from sys_user_role where role_id=#{roleId} and user_id in\n" +
            "<foreach collection=\"userIds\" item=\"userId\" open=\"(\" separator=\",\" close=\")\">\n" +
            "   #{userId}\n" +
            "</foreach>\n" +
            "</script>")
    void cancelAuthUserBatch(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);

    @Select("<script>\n" +
            "insert into sys_user_role(user_id, role_id) values\n" +
            "<foreach item=\"userId\" index=\"index\" collection=\"userIds\" separator=\",\">\n" +
            "(#{userId},#{roleId})\n" +
            "</foreach\n>" +
            "</script>")
    void authUserBatch(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);

    @Select("select count(1) from sys_role_menu where menu_id = #{menuId}")
    Long countByMenuId(@Param("menuId") Long menuId);
}
