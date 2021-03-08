package com.melbsoft.teacherplatform.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.dao.admin.PermissionMapper;
import com.melbsoft.teacherplatform.model.admin.SysMenu;
import com.melbsoft.teacherplatform.model.admin.SysPermission;
import com.melbsoft.teacherplatform.model.admin.SysRole;
import com.melbsoft.teacherplatform.model.admin.vo.SysPermissionQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService {
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Resource
    PermissionMapper permissionMapper;

    public PageInfo<List<SysPermission>> search(SysPermissionQuery query) {
        PageHelper.startPage(query.getPage(), DEFAULT_PAGE_SIZE);
        PageInfo<List<SysPermission>> pagedPermissions = new PageInfo(permissionMapper.search(query));
        return pagedPermissions;
    }

    public List<SysMenu> listMenu(List<SysRole> roles, String view) {
        return permissionMapper.listMenu(roles, view);
    }
}
