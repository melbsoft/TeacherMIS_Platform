package com.melbsoft.teacherplatform.dao.admin;

import com.melbsoft.teacherplatform.model.admin.SysMenu;
import com.melbsoft.teacherplatform.model.admin.SysPermission;
import com.melbsoft.teacherplatform.model.admin.SysRole;
import com.melbsoft.teacherplatform.model.admin.vo.SysPermissionQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {

    int countPermissions(@Param("roleId") Long roleId, @Param("resources") List<Integer> ids, @Param("permission") String permission);

    List<SysPermission> search(@Param("q") SysPermissionQuery query);

    List<SysMenu> listMenu(@Param("roles") List<SysRole> roles, @Param("privilege") String privilege);
}
