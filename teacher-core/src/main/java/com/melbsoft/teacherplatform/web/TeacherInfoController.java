package com.melbsoft.teacherplatform.web;

import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.model.admin.OperationLog;
import com.melbsoft.teacherplatform.model.admin.vo.OptQuery;
import com.melbsoft.teacherplatform.model.teacher.TeacherInfo;
import com.melbsoft.teacherplatform.model.teacher.vo.TeacherInfoVO;
import com.melbsoft.teacherplatform.service.teacher.TeacherInfoService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacherInfo")
@Validated
@Tags(@Tag(name = "教师信息管理功能"))
public class TeacherInfoController {

    @Resource
    private TeacherInfoService teacherInfoService;

    @Operation(summary = "教师信息分页列表查询",
            description = "教师信息分页列表查询",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功")
            })
    @PostMapping("/pageList")
    public ResultMessage<PageInfo<List<TeacherInfo>>> pageList(@RequestBody@ParameterObject TeacherInfoVO teacherInfoVO) {
        return ResultMessage.success(teacherInfoService.pageList(teacherInfoVO));
    }
}
