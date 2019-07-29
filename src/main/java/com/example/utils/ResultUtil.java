package com.example.utils;

import com.example.bean.dto.Result;
import com.example.enums.ResultCode;

/**
 * 结果处理工具类
 *
 * @author HaN
 * @create 2019-04-04 14:06
 */
public class ResultUtil {
    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result success(ResultCode resultCode,Object data) {
        return new Result(resultCode, data);
    }

    public static Result success(ResultCode resultCode,String message,Object data) {
        Result result = new Result(resultCode);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result warn(ResultCode resultCode) {
        return new Result(resultCode);
    }

    public static Result warn(ResultCode resultCode,Object data) {
        return new Result(resultCode,data);
    }

    public static Result warn(ResultCode resultCode,String message,Object data) {
        Result result = new Result(resultCode);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result warn(ResultCode resultCode, String message) {
        Result result = new Result(resultCode);
        result.setMessage(message);
        return result;
    }
}
