package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.component.OpLog;
import com.melbsoft.teacherplatform.model.admin.SysResource;
import com.melbsoft.teacherplatform.model.admin.vo.UserPermissionQuery;
import com.melbsoft.teacherplatform.service.admin.SysUserService;
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
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/sysuser")
@Tags(@Tag(name = "系统用户功能"))
@Validated
public class SysUserController {

    @Resource
    SysUserService sysUserService;


    @Operation(summary = "用户信息查询",
            description = "获取当前登录用户信息",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功"
                    )
            })
    @GetMapping("/info")
    ResultMessage<UserDetails> info() {
        UserDetails userInfo = sysUserService.getUserDetail();
        return ResultMessage.success(userInfo);
    }


    @Operation(summary = "查询用户授权",
            description = "基于不同类别查询用户授权列表",
            responses = {@ApiResponse(responseCode = "200", description = "操作成功")}
    )
    @GetMapping("/permission")
    ResultMessage<List<SysResource>> permission(@Valid @ParameterObject UserPermissionQuery query) {
        List<SysResource> resources = sysUserService.listPermission(query);
        return ResultMessage.success(resources);
    }

    @OpLog(value = "创建用户", target = "loginName+' '+userDisplay +' '+message")
    @Operation(summary = "创建新用户",
            description = "基于用户名与默认密码创建新用户",
            responses = {@ApiResponse(responseCode = "200", description = "操作成功")}
    )
    @PostMapping("/create")
    ResultMessage<Void> create(
            @Parameter(description = "登录名", example = "admin")
            @RequestParam("loginName")
                    String loginName,
            @Parameter(description = "展示用户名", example = "张老师")
            @RequestParam("userDisplay")
                    String userDisplay) {
        sysUserService.create(loginName, userDisplay);
        return ResultMessage.SUCCESS;
    }


    @Operation(summary = "用户密码修改",
            description = "核对旧密码并设置新的用户密码",
            responses = {@ApiResponse(responseCode = "200", description = "操作成功")}
    )
    @PutMapping("/password")
    ResultMessage<Void> changePassword(@Pattern(regexp = "\\w{6,}")
                                       @Parameter(description = "旧密码", example = "_pass")
                                       @RequestParam("old")
                                               String oldPass,
                                       @Pattern(regexp = "\\w{6,}")
                                       @Parameter(description = "新密码", example = "new_pass")
                                       @RequestParam("new")
                                               String newPass) {
        sysUserService.changePass(oldPass, newPass);
        return ResultMessage.SUCCESS;
    }
}


