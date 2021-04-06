package com.melbsoft.teacherplatform.model.admin.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserPermissionQuery {

    @Parameter(description = "资源类型", example = "menu")
    @NotNull
    String resourceType;

    @Parameter(description = "权限类型", example = "view")
    @NotNull
    String privilege;


}
