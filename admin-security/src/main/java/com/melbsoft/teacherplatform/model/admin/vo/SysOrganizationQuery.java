package com.melbsoft.teacherplatform.model.admin.vo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

@Data
public class SysOrganizationQuery {

    @Parameter(description = "组织机构名", example = "economics_trade_s")
    String organizationName;

    @Parameter(description = "组织机构展示名称", example = "经贸学院")
    String organizationDisplay;

    @Parameter(description = "分页页码", example = "1")
    int page = 1;

    @Parameter(description = "是否展示根节点", example = "true")
    boolean showRoot = false;


}
