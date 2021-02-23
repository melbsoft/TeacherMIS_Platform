package com.melbsoft.teacherplatform.dao.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ResourceMapper {
    Integer findResourceID(@Param("resourceName") String resourceName, @Param("resourceType") String resourceType);

    Integer findParentId(@Param("resourceID")Integer id);
}
