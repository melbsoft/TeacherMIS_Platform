package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.common.teacher.CommonInstance;
import com.melbsoft.teacherplatform.model.admin.SysResource;
import com.melbsoft.teacherplatform.model.teacher.DiagramObject;
import com.melbsoft.teacherplatform.model.teacher.vo.DataQuery;
import com.melbsoft.teacherplatform.service.teacher.DiagramService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diagram")
@Validated
@Tags(@Tag(name = "数据报表功能"))
public class DiagramController {
    @Resource
    DiagramService diagramService;
    @GetMapping("/_search")
    public ResultMessage<DiagramObject> demo(@ParameterObject DataQuery q) {
        return ResultMessage.success(diagramService.query(q));
    }
}
