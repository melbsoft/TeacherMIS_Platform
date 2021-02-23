package com.melbsoft.teacherplatform.dao.admin;

import com.melbsoft.teacherplatform.model.admin.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    SysUser findUser(@Param("userName") String userName);

    int updatePass(@Param("userName") String username, @Param("oldPass") String oldPass, @Param("newPass") String newPass);

    int insertUser(@Param("userName") String userName, @Param("encodedPass") String encodedPass, @Param("userDisplay") String userDisplay);

    String loadPass(@Param("userName") String username);
}
