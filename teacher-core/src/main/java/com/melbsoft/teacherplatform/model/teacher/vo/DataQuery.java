package com.melbsoft.teacherplatform.model.teacher.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class DataQuery {
    @Parameter(description = "报表数据索引号", example = "1")
    long index;
}
