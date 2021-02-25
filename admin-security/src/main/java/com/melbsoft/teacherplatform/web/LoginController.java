package com.melbsoft.teacherplatform.web;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
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



    @GetMapping("/login")
    ResultMessage login() {
        return ResultMessage.SUCCESS;
    }


}
