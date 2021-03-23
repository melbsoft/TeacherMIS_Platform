package com.melbsoft.teacherplatform.model.admin.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class DictQuery {

    @Parameter(description = "字典项类型", example = "menu")
    String dictType;

    @Parameter(description = "分页页码", example = "1")
    int page = 1;


}
