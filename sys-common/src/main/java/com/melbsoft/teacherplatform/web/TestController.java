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
@RequestMapping("/test")
public class TestController {
    @Resource
    CacheDemoService cacheDemoService;
    @Resource
    HazelcastInstance hazelcastInstance;


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
