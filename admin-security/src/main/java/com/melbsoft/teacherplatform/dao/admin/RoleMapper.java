package com.melbsoft.teacherplatform.dao.admin;

import com.melbsoft.teacherplatform.model.admin.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<SysRole> listRolesByUserID(@Param("userID") long userID);

    List<SysRole> listRolesByUserName(@Param("username") String username);

    int insertRole(@Param("roleId") long roleId,@Param("username") String username);
}
