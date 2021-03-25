package com.melbsoft.teacherplatform.dao.teacher;

import com.melbsoft.teacherplatform.model.teacher.DiagramObject;
import com.melbsoft.teacherplatform.model.teacher.vo.DataQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DiagramMapper {
    public DiagramObject query(@Param("q") DataQuery q);
}
