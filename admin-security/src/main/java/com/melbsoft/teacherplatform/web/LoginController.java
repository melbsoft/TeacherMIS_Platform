package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Tags(@Tag(name = "系统登录功能"))
public class LoginController {


    @Operation(summary = "用户登录TOKEN查询",
            description = "获取当前分配的TOKEN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "用户信息", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ResultMessage.class))
                    })
            })
    @GetMapping("/token")
    ResultMessage token() {
        return ResultMessage.SUCCESS;
    }


}
