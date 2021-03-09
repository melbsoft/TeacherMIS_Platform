package com.melbsoft.teacherplatform.model.teacher;

import lombok.*;


import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInfo implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
