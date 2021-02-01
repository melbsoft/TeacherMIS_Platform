package com.melbsoft.teacherplatform.model.admin;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SysRole implements GrantedAuthority {
    Long roleId;
    String roleName;
    String roleDisplay;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
