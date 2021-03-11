package com.melbsoft.teacherplatform.service.admin;

import com.melbsoft.teacherplatform.dao.admin.ResourceMapper;
import com.melbsoft.teacherplatform.model.admin.SysResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "static")
public class ResourceService {
    @Resource
    ResourceMapper resourceMapper;

    @Cacheable(value = {"static"}, key = "'resource_parent_'+#parentId")
    public List<SysResource> listSubResource(Long parentId) {
        log.info("load resource from db parentId={}", parentId);
        return resourceMapper.listResourceByParentId(parentId);
    }

    @Cacheable(value = {"static"}, key = "'resource_'+#id")
    public SysResource findResouce(Long id) {
        log.info("load resource from db id={}", id);
        return resourceMapper.listResourceById(id);
    }
}
