package com.melbsoft.teacherplatform.model.admin.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
public class OptQuery {
    @Parameter(description = "操作类型", example = "用户查询")
    String operationName;
    @Parameter(description = "操作开始时间", example = "2021-03-07 23:59:20")
    @Past
    LocalDateTime startTime = LocalDateTime.now().minusYears(1);
    @Parameter(description = "操作结束时间", example = "2021-03-07 23:59:20")
    @PastOrPresent
    LocalDateTime endTime = LocalDateTime.now();
    @Parameter(description = "操作人", example = "张老师")
    String operator;
    @Parameter(description = "操作对象", example = "自定义操作日志模板内容")
    String target;
    @Parameter(description = "分页页码", example = "1")
    int page = 1;
}
