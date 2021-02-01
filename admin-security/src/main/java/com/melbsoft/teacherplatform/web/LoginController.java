package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @GetMapping("/login")
    ResultMessage login() {
        return ResultMessage.SUCCESS;
    }


}
