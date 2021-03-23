package com.melbsoft.teacherplatform.dao.admin;

import com.melbsoft.teacherplatform.model.admin.SysDict;
import com.melbsoft.teacherplatform.model.admin.SysOrganization;
import com.melbsoft.teacherplatform.model.admin.SysResource;
import com.melbsoft.teacherplatform.model.admin.vo.DictQuery;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationCreate;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationQuery;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationUpdate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceMapper {
    Integer findResourceID(@Param("resourceName") String resourceName, @Param("resourceType") String resourceType);

    Integer findParentId(@Param("resourceID") Integer id);

    List<SysOrganization> searchOrg(@Param("q") SysOrganizationQuery query);

    int create(@Param("info") SysOrganizationCreate info);

    int update(@Param("info") SysOrganizationUpdate info);

    int countSubOrg(@Param("organizationId") Long organizationId);

    int delete(@Param("organizationId") Long organizationId);

    List<SysResource> listResourceByParentId(@Param("parentId") Long parentId);

    SysResource listResourceById(@Param("resourceId") Long resourceId);

    List<SysDict> searchDict(@Param("q") DictQuery query);
}
