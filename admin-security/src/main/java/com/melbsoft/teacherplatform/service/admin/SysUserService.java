package com.melbsoft.teacherplatform.service.admin;

import com.melbsoft.teacherplatform.dao.admin.UserMapper;
import com.melbsoft.teacherplatform.tools.SecurityHelper;
import com.melbsoft.teacherplatform.web.exception.InvalidInputException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserService {

    @Resource
    Argon2PasswordEncoder securityPasswordEncoder;

    @Resource
    UserMapper userMapper;

    /**
     * @return 返回登录用户信息
     */
    public UserDetails getUserDetail() {
        return SecurityHelper.getUserDetails();
    }

    public boolean changePass(String oldPass, String newPass) {
        if(oldPass.equals(newPass))
            throw new InvalidInputException("can not change password to current one");
        int updates = 0;
        UserDetails user = SecurityHelper.getUserDetails();
        String currentPass = userMapper.loadPass(user.getUsername());
        if (securityPasswordEncoder.matches(oldPass, currentPass)) {
            updates = userMapper.updatePass(user.getUsername(), currentPass, securityPasswordEncoder.encode(newPass));
        }
        return updates == 1;
    }


    public boolean create(String loginName, String userDisplay) {
        String encodedPass = securityPasswordEncoder.encode("_pass");
        int updates = userMapper.insertUser(loginName, encodedPass, userDisplay);
        return updates == 1;
    }


}
