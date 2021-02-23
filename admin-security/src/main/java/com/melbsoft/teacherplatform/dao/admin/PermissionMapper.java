package com.melbsoft.teacherplatform.dao.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {

    int findPermissions(@Param("roleId") Long roleId, @Param("resources") List<Integer> ids, @Param("permission") String permission);
}
