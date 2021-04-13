package com.melbsoft.teacherplatform.service.teacher;

import com.melbsoft.teacherplatform.dao.teacher.DiagramMapper;
import com.melbsoft.teacherplatform.model.teacher.DiagramObject;
import com.melbsoft.teacherplatform.model.teacher.vo.DataQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DiagramService {
    @Resource
    DiagramMapper diagramMapper;

    public DiagramObject query(DataQuery q) {
        return diagramMapper.query(q);
    }
}
