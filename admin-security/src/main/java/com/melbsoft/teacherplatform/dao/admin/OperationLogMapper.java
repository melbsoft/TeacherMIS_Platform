package com.melbsoft.teacherplatform.dao.admin;

import com.melbsoft.teacherplatform.model.admin.OperationLog;
import com.melbsoft.teacherplatform.model.admin.vo.OptQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogMapper {

    int save(@Param("log") OperationLog log);

    List<OperationLog> search(@Param("q") OptQuery query);
}
