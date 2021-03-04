package com.melbsoft.teacherplatform.model.admin;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OperationLog {
    private long operationId;
    private String operationName;
    private LocalDateTime operationTime;
    private String operator;
    private String target;
    private String result;

}
