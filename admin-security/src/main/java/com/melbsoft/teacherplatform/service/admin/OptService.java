package com.melbsoft.teacherplatform.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.dao.admin.OperationLogMapper;
import com.melbsoft.teacherplatform.model.admin.OperationLog;
import com.melbsoft.teacherplatform.model.admin.vo.OptQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OptService {

    private static final int DEFAULT_PAGE_SIZE = 3;
    @Resource
    OperationLogMapper operationLogMapper;

    public PageInfo<List<OperationLog>> search(OptQuery query) {
        PageHelper.startPage(query.getPage(), DEFAULT_PAGE_SIZE);
        PageInfo<List<OperationLog>> pagedLogs = new PageInfo(operationLogMapper.search(query));
        return pagedLogs;
    }
}
