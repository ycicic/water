package com.ycicic.framework.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ycicic.common.core.vo.Response;
import com.ycicic.common.exception.ServiceException;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author ycicic
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Response<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestUri, e.getMessage());
        return Response.fail(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUri, e.getMethod());
        return Response.fail("请求方式不支持");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(ServiceException.class)
    public Response<?> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage());
        Integer code = e.getCode();
        return Objects.nonNull(code) ? Response.fail((int) code, e.getMessage()) : Response.fail(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Response<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestUri, e);
        return Response.fail("系统发生异常");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestUri, e);
        return Response.fail("系统发生异常");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Response<?> handleBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        log.error(message);
        return Response.fail(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 参数无效异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error(message);
        return Response.fail(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response<?> handelDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        return Response.fail(HttpStatus.BAD_REQUEST, "数据格式异常");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<?> handleInvalidFormatException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        Throwable cause = e.getCause();
        invalidFormatException:
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) cause;
            JsonMappingException.Reference reference = invalidFormatException.getPath().get(0);
            Object from = reference.getFrom();
            Class<?> aClass = from.getClass();
            String fieldName = reference.getFieldName();
            try {
                Field field = aClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                if (null == annotation) {
                    break invalidFormatException;
                }
                return Response.fail(HttpStatus.BAD_REQUEST, annotation.value() + "格式异常");
            } catch (NoSuchFieldException ex) {
                break invalidFormatException;
            }
        }
        return Response.fail(HttpStatus.BAD_REQUEST, "数据格式异常");
    }

}
