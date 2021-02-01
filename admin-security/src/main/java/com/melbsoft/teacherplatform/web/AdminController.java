package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.web.basic.MessageException;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @PreAuthorize("hasPermission('4:org', 'auth')")
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public ResultMessage<Boolean> auth() throws MessageException {
        return ResultMessage.SUCCESS;
    }
}
