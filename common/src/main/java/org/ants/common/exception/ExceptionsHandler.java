package org.ants.common.exception;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常接管
 *
 * @author Li Jinhui
 * @param <T>
 * @since 2018/12/6
 */
@RestControllerAdvice
public class ExceptionsHandler {

    private final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    /**
     * 基本异常
     * 
     * @param <T>
     * 
     * @since 2018/12/6
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        logger.error(e.getMessage(), e);
        return new Result(ErrorConstants.SE_UNKNOW);
    }

    /**
     * 404异常
     * 
     * @since 2018/12/6
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result noHandlerFoundException() {
        return new Result(ErrorConstants.SE_NOT_FOUND);
    }

    /**
     * 服务异常
     * 
     * @since 2018/12/6
     */
    @ExceptionHandler(ServiceException.class)
    public Result serviceException(ServiceException e) {
        return new Result(e.getCode(), e.getMessage());
    }
}