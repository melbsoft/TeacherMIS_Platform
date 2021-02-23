package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.service.CacheDemoService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class LoginController {

    @Resource
    CacheDemoService cacheDemoService;


    @GetMapping("/login")
    ResultMessage login() {
        return ResultMessage.SUCCESS;
    }

    @GetMapping("/session")
    public Object session(HttpSession session) {
        String sessionId = session.getId();

        return session.getAttributeNames();
    }

    @GetMapping("/cache")
    public Object cache() {
        String hi = cacheDemoService.hi("leo");
        return ResultMessage.success(hi);
    }
}
