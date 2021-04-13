package com.melbsoft.teacherplatform.model.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "教师id", example = "1")
    private Integer id;

    @Schema(description = "教师姓名", example = "view")
    private String teacherName;

    @Schema(description = "护照号", example = "view")
    private String passportNo;

    @Schema(description = "性别", example = "男")
    private String sex;

    @Schema(description = "国家id", example = "1")
    private Integer nationId;

    @Schema(description = "教师类型", example = "outside_employed")
    private String teacherType;

    @Schema(description = "学院id", example = "1")
    private Integer collegeId;

    @Schema(description = "聘用方式", example = "view")
    private String employType;

    @Schema(description = "聘用时间", example = "五年")
    private String employYear;

    @Schema(description = "聘用信息", example = "备注")
    private String employRemark;

    @Schema(description = "教师来源", example = "外部聘用")
    private String teacherSource;

    @Schema(description = "审批状态", example = "0，待审批；1，审批通过；2，审批不通过")
    private Integer approvalStatus;

    @Schema(description = "删除标识", example = "1，正常；0，删除")
    private Integer usableStatus;

    @Schema(description = "创建时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "更新时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    @Schema(description = "操作员ID", example = "1")
    private Integer operatorId;
}
