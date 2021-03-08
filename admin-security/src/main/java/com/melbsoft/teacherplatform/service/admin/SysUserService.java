package com.melbsoft.teacherplatform.service.admin;

import com.melbsoft.teacherplatform.dao.admin.RoleMapper;
import com.melbsoft.teacherplatform.dao.admin.UserMapper;
import com.melbsoft.teacherplatform.model.admin.SysMenu;
import com.melbsoft.teacherplatform.model.admin.SysRole;
import com.melbsoft.teacherplatform.tools.SecurityHelper;
import com.melbsoft.teacherplatform.web.exception.ForbiddenException;
import com.melbsoft.teacherplatform.web.exception.InvalidInputException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserService {

    public static final String DEFAULT_PASS = "Pass_1234";
    @Resource
    Argon2PasswordEncoder securityPasswordEncoder;

    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    PermissionService permissionService;

    /**
     * @return 返回登录用户信息
     */
    public UserDetails getUserDetail() {
        return SecurityHelper.getUserDetails();
    }

    public void changePass(String oldPass, String newPass) {
        if (oldPass.equals(newPass))
            throw new InvalidInputException("unchanged password!");
        int updates = 0;
        UserDetails user = SecurityHelper.getUserDetails();
        String currentPass = userMapper.loadPass(user.getUsername());
        if (securityPasswordEncoder.matches(oldPass, currentPass)) {
            updates = userMapper.updatePass(user.getUsername(), currentPass, securityPasswordEncoder.encode(newPass));
        }
        if (updates != 1) {
            throw new ForbiddenException(String.format("password can not be change! username:%s old:%s ", user.getUsername(), oldPass));
        }
    }

    public void create(String loginName, String userDisplay) {
        String encodedPass = securityPasswordEncoder.encode(DEFAULT_PASS);
        int rows = userMapper.insertUser(loginName, encodedPass, userDisplay);
        if (rows != 1) {
            throw new ForbiddenException("duplicate user found!");
        }
    }

    public List<SysMenu> listMenu() {
        String userName = SecurityHelper.getUserDetails().getUsername();
        List<SysRole> roles = roleMapper.listRolesByUserName(userName);
       return permissionService.listMenu(roles, "view");
    }
}
