package com.melbsoft.teacherplatform.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.dao.admin.ResourceMapper;
import com.melbsoft.teacherplatform.model.admin.SysOrganization;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationCreate;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationQuery;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationUpdate;
import com.melbsoft.teacherplatform.web.exception.ForbiddenException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgangizationService {
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Resource
    ResourceMapper resourceMapper;

    public PageInfo<List<SysOrganization>> search(SysOrganizationQuery query) {
        PageHelper.startPage(query.getPage(), DEFAULT_PAGE_SIZE);
        PageInfo<List<SysOrganization>> pagedOrganizations = new PageInfo(resourceMapper.search(query));
        return pagedOrganizations;
    }

    public void create(SysOrganizationCreate info) {
        int rows = resourceMapper.create(info);
        if (rows != 1) {
            throw new ForbiddenException("organization exists!");
        }
    }

    public void update(SysOrganizationUpdate info) {
        int rows = resourceMapper.update(info);
        if (rows != 1) {
            throw new ForbiddenException("organization not found!");
        }
    }

    public void delete(Long organizationId) {
        int count = resourceMapper.countSubOrg(organizationId);
        if (count > 0) {
            throw new ForbiddenException("sub-organization found!");
        }
        int rows = resourceMapper.delete(organizationId);
        if (rows != 1) {
            throw new ForbiddenException("organization not found!");
        }
    }
}
