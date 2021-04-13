package com.melbsoft.teacherplatform.dao.teacher;

import com.melbsoft.teacherplatform.model.teacher.TeacherInfo;
import com.melbsoft.teacherplatform.model.teacher.vo.TeacherInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherInfoMapper {

    /**
     * 新增教师信息
     * @param teacherInfo
     * @return
     */
    int insertTeacher(TeacherInfo teacherInfo);

    /**
     * 查询教师信息列表
     * @param teacherInfo
     * @return
     */
    List<TeacherInfo> list(@Param("q") TeacherInfoVO teacherInfo);
}
