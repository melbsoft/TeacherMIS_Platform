package com.melbsoft.teacherplatform.web;

import com.github.pagehelper.PageInfo;
import com.melbsoft.teacherplatform.model.admin.SysOrganization;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationCreate;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationQuery;
import com.melbsoft.teacherplatform.model.admin.vo.SysOrganizationUpdate;
import com.melbsoft.teacherplatform.service.admin.OrgangizationService;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/org")
@Tags(@Tag(name = "组织机构管理功能"))
@Validated
public class OrganizationController {

    @Resource
    OrgangizationService organgizationService;

    @Operation(summary = "查询组织结构列表",
            description = "支持基于不同条件过滤列表",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功" )
            })
    @GetMapping("/_search")
    ResultMessage<PageInfo<List<SysOrganization>>> search(@Valid @ParameterObject SysOrganizationQuery query) {
        PageInfo<List<SysOrganization>> plist = organgizationService.search(query);
        return ResultMessage.success(plist);
    }

    @Operation(summary = "添加组织机构",
            description = "添加新组织机构节点",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功"
                    )
            })
    @PostMapping("/create")
    ResultMessage<Boolean> create(@Valid @ParameterObject SysOrganizationCreate info) {
        organgizationService.create(info);
        return ResultMessage.SUCCESS;
    }

    @Operation(summary = "更新组织机构",
            description = "根据ID，更新组织机构节点的某些属性",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功"
                    )
            })
    @PutMapping("/update")
    ResultMessage<Boolean> update(@Valid @ParameterObject SysOrganizationUpdate info) {
        organgizationService.update(info);
        return ResultMessage.SUCCESS;
    }

    @Operation(summary = "删除组织机构",
            description = "根据ID，删除组织机构节点，需要保证节点下不再有其他子节点",
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功"
                    )
            })
    @DeleteMapping("/delete")
    ResultMessage<Boolean> delete(@Valid
                                  @Min(0)
                                  @Parameter(description = "组织机ID", example = "1")
                                  @RequestParam("organizationId")
                                          Long organizationId) {
        organgizationService.delete(organizationId);
        return ResultMessage.SUCCESS;

    }
}
