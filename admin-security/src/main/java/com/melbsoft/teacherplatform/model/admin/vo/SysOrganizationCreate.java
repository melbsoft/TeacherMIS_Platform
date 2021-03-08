package com.melbsoft.teacherplatform.model.admin.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SysOrganizationCreate {
    @Parameter(description = "组织机构名", example = "economics_trade_s")
    @NotBlank
    String organizationName;

    @Parameter(description = "组织机构展示名称", example = "经贸学院")
    @NotBlank
    String organizationDisplay;

    @Parameter(description = "组织机构序号", example = "1")
    @Min(0)
    Integer sequenceNum;

    @Parameter(description = "父级组织机ID", example = "1")
    @NotNull
    Long parentOrganizationId;
}
