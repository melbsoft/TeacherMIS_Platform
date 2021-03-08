package com.melbsoft.teacherplatform.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class SysPermission {
    String permissionId;
    String permissionDisplay;
    String resourceTypeDisplay;
    String resourceNameDisplay;
    String privilege;
    List<SysRole> roles;
}
