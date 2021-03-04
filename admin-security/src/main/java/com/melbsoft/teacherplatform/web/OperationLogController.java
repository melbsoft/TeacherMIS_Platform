package com.melbsoft.teacherplatform.web;

import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.model.admin.OperationLog;
import com.melbsoft.teacherplatform.model.admin.vo.OptQuery;
import com.melbsoft.teacherplatform.service.admin.OptService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/optlog")
@Api(tags = {"系统操作日志功能"})
@Validated
public class OperationLogController {
    @Resource
    OptService optService;

    @GetMapping("/_search")
    ResultMessage<PageInfo<List<OperationLog>>> search(@Valid OptQuery query) {
        return ResultMessage.success(optService.search(query));
    }

}
