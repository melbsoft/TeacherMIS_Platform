package com.melbsoft.teacherplatform.model.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysResource implements Serializable {
    @Schema(description = "资源ID", example = "1")
    Long resourceId;

    @Schema(description = "父级资源ID", example = "0")
    Long parentId;

    @Schema(description = "资源类型", example = "menu")
    String resourceType;

    @Schema(description = "资源类型展示名", example = "菜单")
    String resourceTypeDisplay;

    @Schema(description = "资源名", example = "TeacherInfo")
    String resourceName;

    @Schema(description = "资源展示名", example = "教师信息管理")
    String resourceNameDisplay;

    @Schema(description = "资源序号", example = "1")
    Long sequenceNum;

    transient List<SysResource> subResource;


}
