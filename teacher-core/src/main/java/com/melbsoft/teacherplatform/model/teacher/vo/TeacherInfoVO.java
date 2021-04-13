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

    /**
     * 0:允许编辑 1：禁止编辑
     */
    private Integer enableEdit;

    /**
     * 0:可见 1：不可见
     */
    private Integer enableView;

    int page = 1;
}
