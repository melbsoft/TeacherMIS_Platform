package com.melbsoft.teacherplatform.model.teacher.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherInfoVO {
    private Integer id;

    private String teacherName;

    private String passportNo;

    private String sex;

    private Integer nationId;

    private String teacherType;

    private Integer collegeId;

    private String employType;

    private String employYear;

    private String employRemark;

    private String teacherSource;

    private Integer approvalStatus;

    private Integer usableStatus;

    private Date createTime;

    private Date operateTime;

    private Integer operatorId;

    int page = 1;
}
