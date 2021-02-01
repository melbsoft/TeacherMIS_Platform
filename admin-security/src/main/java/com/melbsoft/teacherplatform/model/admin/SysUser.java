package com.melbsoft.teacherplatform.model.admin;

import lombok.Data;

@Data
public class SysUser {

    private long userID;
    private String loginName;
    private String password;

}
