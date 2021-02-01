package com.melbsoft.teacherplatform.dao.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<String> listRolesByUserID(@Param("userID") long userID);
}
