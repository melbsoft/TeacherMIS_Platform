package com.melbsoft.teacherplatform.model.admin.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
public class OptQuery {
    String operationName;
    @Past
    LocalDateTime startTime = LocalDateTime.now().minusYears(1);
    @PastOrPresent
    LocalDateTime endTime = LocalDateTime.now();
    String operator;
    String target;
    int page = 1;
}
