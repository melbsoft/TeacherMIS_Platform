package com.melbsoft.teacherplatform.web;

import com.melbsoft.teacherplatform.web.exception.InvalidInputException;
import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

public class AdminController {


    //    @PreAuthorize("hasPermission('foreign_language:org', 'auth')")
    @PreAuthorize("isAuthenticated()")
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
