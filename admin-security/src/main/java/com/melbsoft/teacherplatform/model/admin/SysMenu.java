package com.melbsoft.teacherplatform.model.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysMenu {
    Long rpId;
    @Schema(description = "菜单名", example = "TeacherInfo")
    String menuName;
    @Schema(description = "菜单展示名", example = "教师信息管理")
    String menuDisplay;
}
