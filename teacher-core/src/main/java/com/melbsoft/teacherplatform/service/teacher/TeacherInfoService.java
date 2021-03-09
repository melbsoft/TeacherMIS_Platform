package com.melbsoft.teacherplatform.service.teacher;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.dao.teacher.TeacherInfoMapper;
import com.melbsoft.teacherplatform.model.admin.OperationLog;
import com.melbsoft.teacherplatform.model.teacher.TeacherInfo;
import com.melbsoft.teacherplatform.model.teacher.vo.TeacherInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherInfoService {

    @Resource
    private TeacherInfoMapper teacherInfoMapper;

    private static final int DEFAULT_PAGE_SIZE = 3;

    public PageInfo<List<TeacherInfo>> pageList(TeacherInfoVO teacherInfo){
        PageHelper.startPage(teacherInfo.getPage(), DEFAULT_PAGE_SIZE);
        PageInfo<List<TeacherInfo>> result = new PageInfo(teacherInfoMapper.list(teacherInfo));
        return result;
    }
}
