package com.melbsoft.teacherplatform.model.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OperationLog {
    @Schema(description = "操作日志ID", example = "1")
    private long operationId;
    @Schema(description = "操作类型", example = "用户查询")
    private String operationName;
    @Schema(description = "操作时间", example = "2021-03-07 23:59:20")
    private LocalDateTime operationTime;
    @Schema(description = "操作人", example = "张老师")
    private String operator;
    @Schema(description = "操作对象", example = "自定义操作日志模板内容")
    private String target;
    @Schema(description = "操作结果", example = "ok")
    private String result;

}
