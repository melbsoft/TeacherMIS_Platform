package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.service.admin.SysUserService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/sysuser")
@Api(tags = {"系统用户功能"})
@Validated
public class SysUserController {

    @Resource
    SysUserService sysUserService;

    @Operation(summary = "用户信息查询",
            description = "获取当前登录用户信息",
            responses = {
                    @ApiResponse(responseCode = "200", description = "用户信息", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetails.class))
                    })
            })
    @GetMapping("/info")
    ResultMessage<UserDetails> info() {
        UserDetails userInfo = sysUserService.getUserDetail();
        return ResultMessage.success(userInfo);
    }


    @Operation(summary = "创建新用户",
            description = "基于用户名与默认密码创建新用户",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                    })

            })
    @PostMapping("/create")
    ResultMessage<Boolean> create(
            @Parameter(name = "登录名", example = "admin")
            @RequestParam("loginName")
                    String loginName,
            @Parameter(name = "展示用户名", example = "张老师")
            @RequestParam("userDisplay")
                    String userDisplay) {
        boolean success = sysUserService.create(loginName, userDisplay);
        return ResultMessage.success(success);
    }

    @PutMapping("/password")
    @Operation(summary = "用户密码修改",
            description = "核对旧密码并设置新的用户密码",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                    })

            })
    ResultMessage<Boolean> changePassword(
            @Pattern(regexp = "\\w{6,}")
            @Parameter(name = "旧密码", example = "_pass")
            @RequestParam("old")
                    String oldPass,
            @Pattern(regexp = "\\w{6,}")
            @Parameter(name = "新密码", example = "new_pass")
            @RequestParam("new")
                    String newPass) {
        boolean success = sysUserService.changePass(oldPass, newPass);
        return ResultMessage.success(success);
    }

}
