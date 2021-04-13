package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.model.teacher.DiagramObject;
import com.melbsoft.teacherplatform.model.teacher.vo.DataQuery;
import com.melbsoft.teacherplatform.service.teacher.DiagramService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/diagram")
@Validated
@Tags(@Tag(name = "数据报表功能"))
public class DiagramController {

    @Resource
    DiagramService diagramService;


    @Operation(summary = "查询报表数据",
            description = "通过index序号，获取对应报表数据",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功")
            })
    @GetMapping("/_search")
    public ResultMessage<DiagramObject> demo(@ParameterObject DataQuery q) {
        return ResultMessage.success(diagramService.query(q));
    }
}
