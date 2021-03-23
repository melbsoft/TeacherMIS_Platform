package com.melbsoft.teacherplatform.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.dao.admin.ResourceMapper;
import com.melbsoft.teacherplatform.model.admin.SysDict;
import com.melbsoft.teacherplatform.model.admin.vo.DictQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    @Resource
    ResourceMapper resourceMapper;


    public PageInfo<List<SysDict>> search(DictQuery query) {
        PageHelper.startPage(query.getPage(), DEFAULT_PAGE_SIZE);
        PageInfo<List<SysDict>> pagedDicts = new PageInfo(resourceMapper.searchDict(query));
        return pagedDicts;
    }
}
