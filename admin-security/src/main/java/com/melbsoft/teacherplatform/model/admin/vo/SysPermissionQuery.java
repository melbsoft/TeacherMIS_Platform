package com.melbsoft.teacherplatform.model.admin.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class SysPermissionQuery {
    String permissionDisplay;

    int page = 1;
}
