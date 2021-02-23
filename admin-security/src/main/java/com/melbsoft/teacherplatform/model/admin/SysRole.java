package com.melbsoft.teacherplatform.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SysRole implements GrantedAuthority {
    @JsonIgnore
    Long roleId;
    String roleName;
    String roleDisplay;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return "ROLE_" + roleName;
    }
}
