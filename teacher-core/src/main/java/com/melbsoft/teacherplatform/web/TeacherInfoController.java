package com.melbsoft.teacherplatform.web;

import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.common.teacher.CommonInstance;
import com.melbsoft.teacherplatform.model.admin.OperationLog;
import com.melbsoft.teacherplatform.model.admin.SysResource;
import com.melbsoft.teacherplatform.model.admin.SysUser;
import com.melbsoft.teacherplatform.model.admin.vo.OptQuery;
import com.melbsoft.teacherplatform.model.admin.vo.UserPermissionQuery;
import com.melbsoft.teacherplatform.model.teacher.TeacherInfo;
import com.melbsoft.teacherplatform.model.teacher.dto.TeacherInfoDto;
import com.melbsoft.teacherplatform.model.teacher.vo.TeacherInfoVO;
import com.melbsoft.teacherplatform.service.admin.ResourceService;
import com.melbsoft.teacherplatform.service.admin.SysUserService;
import com.melbsoft.teacherplatform.service.teacher.TeacherInfoService;
import com.melbsoft.teacherplatform.tools.SecurityHelper;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacherInfo")
@Validated
@Tags(@Tag(name = "教师信息管理功能"))
public class TeacherInfoController {

    @Resource
    private TeacherInfoService teacherInfoService;

    @Resource
    private ResourceService resourceService;

    @Resource
    private SysUserService sysUserService;

    @Operation(summary = "获取resource列表（teacherType）",
            description = "获取resource列表，教师信息专用",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功")
            })
    @PostMapping("/getTeachrTypeList")
    public ResultMessage<List<SysResource>> getTeachrTypeList(@ParameterObject Map paramMap) {
        return ResultMessage.success(resourceService.searchByResourceTypeAndName(CommonInstance.TEACHER_TYPE));
    }

    @Operation(summary = "教师信息分页列表查询",
            description = "教师信息分页列表查询",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功")
            })
    @PostMapping("/pageList")
    public ResultMessage<PageInfo<List<TeacherInfoDto>>> pageList(@ParameterObject TeacherInfoVO teacherInfoVO) {
        return ResultMessage.success(teacherInfoService.pageList(teacherInfoVO));
    }

    @PostMapping("/create")
    ResultMessage<Void> create(TeacherInfo teacherInfo) {
        String userName = SecurityHelper.getUserDetails().getUsername();
        SysUser sysUser = sysUserService.findUser(userName);
        teacherInfo.setOperatorId((int)(sysUser.getUserID()));
        UserPermissionQuery permissionQuery = new UserPermissionQuery();
        permissionQuery.setResourceType("org");
        permissionQuery.setPrivilege("auth");
        teacherInfoService.create(teacherInfo);
        return ResultMessage.SUCCESS;
    }
}
