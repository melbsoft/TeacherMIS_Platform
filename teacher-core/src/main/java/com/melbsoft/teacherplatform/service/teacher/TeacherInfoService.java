package com.melbsoft.teacherplatform.service.teacher;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.melbsoft.teacherplatform.dao.teacher.TeacherInfoMapper;
import com.melbsoft.teacherplatform.model.admin.SysResource;
import com.melbsoft.teacherplatform.model.teacher.TeacherInfo;
import com.melbsoft.teacherplatform.model.teacher.dto.TeacherInfoDto;
import com.melbsoft.teacherplatform.model.teacher.vo.TeacherInfoVO;
import com.melbsoft.teacherplatform.service.admin.ResourceService;
import com.melbsoft.teacherplatform.web.exception.ForbiddenException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherInfoService {

    @Resource
    private TeacherInfoMapper teacherInfoMapper;

    @Resource
    private ResourceService resourceService;

    private static final int DEFAULT_PAGE_SIZE = 10;


    public PageInfo<List<TeacherInfoDto>> pageList(TeacherInfoVO teacherInfo){
        PageHelper.startPage(teacherInfo.getPage(), DEFAULT_PAGE_SIZE);
        List<TeacherInfoDto> teacherInfoDtoList = Lists.newArrayList();
        List<TeacherInfo> teacherInfoList = teacherInfoMapper.list(teacherInfo);

        if (teacherInfoList.size() > 0) {
            //获取sysResource教师类型名称
            List<SysResource> teacherTypeNameList = resourceService.searchByResourceTypeAndName("teacher_type");
            if(teacherTypeNameList.size()==0){
                throw new ForbiddenException("Type doesn't exist");
            }
            Map<String, String> teacherTypeNameMap = teacherTypeNameList.stream().collect(Collectors.toMap(SysResource::getResourceName, SysResource::getResourceNameDisplay));

            for (TeacherInfo info : teacherInfoList){
                TeacherInfoDto teacherInfoDto = new TeacherInfoDto();
                BeanUtils.copyProperties(info,teacherInfoDto);
                teacherInfoDto.setTeacherType(teacherTypeNameMap.get(info.getTeacherType()));
                teacherInfoDtoList.add(teacherInfoDto);
            }
        }
        PageInfo<List<TeacherInfoDto>> result = new PageInfo(teacherInfoDtoList);
        return result;
    }
}
