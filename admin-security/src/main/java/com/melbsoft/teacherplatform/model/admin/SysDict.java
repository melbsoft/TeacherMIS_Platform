package com.melbsoft.teacherplatform.model.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysDict {

    @Schema(description = "字典项ID", example = "1")
    Long dictId;
    @Schema(description = "字典项名", example = "economics_trade_s")
    String dictName;
    @Schema(description = "字典项展示名称", example = "经贸学院")
    String dictDisplay;
    @Schema(description = "父级字典项展示名称", example = "安徽师范大学")
    String parentDisplay;
    @Schema(description = "展示序号", example = "1")
    int sequenceNum;
}
