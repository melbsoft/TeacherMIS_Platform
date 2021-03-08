package com.melbsoft.teacherplatform.web;

import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.model.admin.SysPermission;
import com.melbsoft.teacherplatform.model.admin.vo.SysPermissionQuery;
import com.melbsoft.teacherplatform.service.admin.PermissionService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
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
@RequestMapping("/permit")
@Tags(@Tag(name = "授权管理功能"))
@Validated
public class PermissionController {

    @Resource
    PermissionService permissionService;

    @GetMapping("/_search")
    ResultMessage<PageInfo<List<SysPermission>>> search(@Valid @ParameterObject SysPermissionQuery query) {
        PageInfo<List<SysPermission>> plist = permissionService.search(query);
        return ResultMessage.success(plist);
    }
}
