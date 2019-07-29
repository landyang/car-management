package com.example.exception;

import com.example.bean.dto.Result;
import com.example.enums.ResultCode;
import com.example.service.impl.PeccancyServiceImpl;
import com.example.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理
 * @author HaN
 * @create 2019-04-24 16:39
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 文件上传超限异常处理
     * @param e 做处理时用 可有可无
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件上传超限异常",e);
        return ResultUtil.warn(ResultCode.ERROR_FILE_SIZE_EXCEEDED);
    }
}
