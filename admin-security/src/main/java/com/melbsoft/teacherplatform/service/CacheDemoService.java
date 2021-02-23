package com.melbsoft.teacherplatform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@CacheConfig(cacheNames = "instruments")
@Slf4j
public class CacheDemoService {
    @Autowired
    private CacheManager cacheManager;

    @Cacheable(key = "#name")
    public String hi(String name) {
        String msg = "hi" + name + new Random().nextInt(10000);
        log.info("load hi message {}", msg);
        log.info("Using cache manager: " + cacheManager.getClass().getName());
        return msg;
    }
}
