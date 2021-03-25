package com.melbsoft.teacherplatform.model.teacher;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

@Data
public class DiagramObject {
    @JsonRawValue
    String content;
}
