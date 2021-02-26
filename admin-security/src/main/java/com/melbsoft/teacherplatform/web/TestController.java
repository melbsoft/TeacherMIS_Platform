package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import com.melbsoft.teacherplatform.web.exception.InvalidInputException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


//        @PreAuthorize("hasPermission('foreign_language:org', 'auth') AND hasPermission('teacher_info:menu', 'view')")

    //    @PreAuthorize("hasRoles('abc')")
    @RequestMapping(value = "info/{type}", method = RequestMethod.GET)
    public ResultMessage<Object> info(@PathVariable("type") String type) throws InvalidInputException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        switch (type) {
            case "basic": {
                return ResultMessage.success(auth.getPrincipal());
            }
            case "auth": {
                return ResultMessage.success(auth.getAuthorities());
            }
        }
        return ResultMessage.INVALID;
    }


}
