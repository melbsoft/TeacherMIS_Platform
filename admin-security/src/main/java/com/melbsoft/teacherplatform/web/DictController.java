package com.melbsoft.teacherplatform.web;

import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.model.admin.SysDict;
import com.melbsoft.teacherplatform.model.admin.SysOrganization;
import com.melbsoft.teacherplatform.model.admin.vo.DictQuery;
import com.melbsoft.teacherplatform.service.admin.DictService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dict")
@Tags(@Tag(name = "系统字典功能"))
public class DictController {

    @Resource
    DictService dictService;

    @GetMapping("/_search")
    ResultMessage<PageInfo<List<SysDict>>> search(@Valid @ParameterObject DictQuery query) {
        PageInfo<List<SysDict>> plist = dictService.search(query);
        return ResultMessage.success(plist);
    }
}
