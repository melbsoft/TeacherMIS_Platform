package com.melbsoft.teacherplatform.model.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysOrganization {

    @Schema(description = "组织机构ID", example = "1")
    Long organizationId;
    @Schema(description = "组织机构名", example = "economics_trade_s")
    String organizationName;
    @Schema(description = "组织机构展示名称", example = "经贸学院")
    String organizationDisplay;
    @Schema(description = "父级组织展示名称", example = "安徽师范大学")
    String parentDisplay;
    @Schema(description = "展示序号", example = "1")
    int sequenceNum;
}
