package com.melbsoft.teacherplatform.dao.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PermissionMapper {

    int findPermissions(@Param("roleId") Long roleId, @Param("targetId") String targetId, @Param("targetType") String targetType, @Param("permission") String permission);
}
