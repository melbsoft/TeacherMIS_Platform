package com.melbsoft.teacherplatform.web;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.melbsoft.teacherplatform.service.CacheDemoService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import com.melbsoft.teacherplatform.web.exception.InvalidInputException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    CacheDemoService cacheDemoService;
    @Resource
    HazelcastInstance hazelcastInstance;

//        @PreAuthorize("hasPermission('foreign_language:org', 'auth') AND hasPermission('teacher_info:menu', 'view')")

//    @PreAuthorize("hasRoles('abc')")
    @RequestMapping(value = "info/{type}", method = RequestMethod.GET)
    public ResultMessage<Object> info(@PathVariable("type") String type) throws InvalidInputException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        switch (type) {
            case "basic": {
                return ResultMessage.success(auth.getPrincipal());
            }
            case "auth": {
                return ResultMessage.success(auth.getAuthorities());
            }
        }
        return ResultMessage.INVALID;
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

    @GetMapping("/mapput")
    public Object mapput() {
        IMap<String, Integer> statistic = hazelcastInstance.getMap("statistic");
        Integer count = statistic.putIfAbsent("count", 1);
        if (count != null) {
            statistic.replace("count", count, count + 1);
        }
        return 1F;
    }

    @GetMapping("/mapget")
    public Integer mapget() {
        IMap<String, Integer> statistic = hazelcastInstance.getMap("statistic");
        return statistic.get("count");
    }
}
