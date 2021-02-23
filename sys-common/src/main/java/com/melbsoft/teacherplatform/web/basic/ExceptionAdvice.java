package com.melbsoft.teacherplatform.web.basic;

import com.melbsoft.teacherplatform.web.exception.FailOperationException;
import com.melbsoft.teacherplatform.web.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


/**
 * @author leo wang
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(value = {
            BindException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            NoHandlerFoundException.class,
            InvalidInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultMessage parameterExceptionHandler(WebRequest request, Exception e) {
        logger.warn("invalid request! {} ", request, e);
        return ResultMessage.INVALID;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultMessage handleValidationException(ConstraintViolationException e) {
        StringBuilder detail = new StringBuilder();
        for (ConstraintViolation<?> s : e.getConstraintViolations()) {
            detail.append(s.getMessage()).append(";");
        }
        logger.warn("constraint violation! {} ", detail, e);
        return ResultMessage.INVALID;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResultMessage accessDenyHandler(WebRequest request, Throwable e) {
        logger.error("message process error! {} ", request, e);
        return ResultMessage.UN_AUTH;
    }


    @ExceptionHandler(FailOperationException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResultMessage sysErrorHandler(WebRequest request, FailOperationException e) {
        logger.error("message process error! {} ", request, e);
        return ResultMessage.fail(e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResultMessage globalHandler(WebRequest request, Throwable e) {
        logger.error("system error! {} ", request, e);
        return ResultMessage.ERROR;
    }


}
