package com.melbsoft.teacherplatform.model.teacher.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherInfoDto {
    private Integer id;

    private String teacherName;

    private String passportNo;

    private String sex;

    private Integer nationId;

    private String nationName;

    private String teacherType;

    private Integer collegeId;

    private String collegeName;

    private String employType;

    private String employYear;

    private String employRemark;

    private String teacherSource;

    private Integer approvalStatus;

    private Integer usableStatus;

    private Date createTime;

    private Date operateTime;

    private Integer operatorId;
}
