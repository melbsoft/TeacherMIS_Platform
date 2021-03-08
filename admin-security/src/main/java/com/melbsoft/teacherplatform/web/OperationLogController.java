package com.melbsoft.teacherplatform.web;

import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.model.admin.OperationLog;
import com.melbsoft.teacherplatform.model.admin.vo.OptQuery;
import com.melbsoft.teacherplatform.service.admin.OptService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/optlog")
@Tags(@Tag(name = "系统操作日志功能"))
@Validated
public class OperationLogController {
    @Resource
    OptService optService;

    @Operation(summary = "用户日志查询",
            description = "获取当前登录用户日志信息",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功")
            })
    @GetMapping("/_search")
    ResultMessage<PageInfo<List<OperationLog>>> search(@Valid @ParameterObject OptQuery query) {
        return ResultMessage.success(optService.search(query));
    }

}
