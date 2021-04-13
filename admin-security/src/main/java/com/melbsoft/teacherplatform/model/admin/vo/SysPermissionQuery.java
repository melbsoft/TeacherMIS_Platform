package com.melbsoft.teacherplatform.model.admin.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class SysPermissionQuery {

    @Parameter(description = "权限展示名称", example = "查看教师信息管理菜单")
    String permissionDisplay;

    @Parameter(description = "资源类型", example = "menu")
    String resourceType;

    @Parameter(description = "资源类型展示名", example = "菜单")
    String resourceTypeDisplay;

    @Parameter(description = "资源名", example = "TeacherInfo")
    String resourceName;

    @Parameter(description = "资源展示名", example = "教师信息管理")
    String resourceNameDisplay;

    @Parameter(description = "权限类型", example = "view")
    String privilege;

    int page = 1;
}
