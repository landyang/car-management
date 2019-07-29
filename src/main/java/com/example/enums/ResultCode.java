package com.example.enums;

/**
 * 结果枚举
 *
 * @author HaN
 * @create 2019-03-31 10:33
 */
public enum ResultCode {

    SUCCESS(0, "请求成功"),
    WARN(-1, "网络异常，请稍后重试"),

    ERROR_UNACCREDITED(4002,"没有访问权限"),

    ERROR_DATA_ABNORMAL(5000, "数据库异常"),
    ERROR_DATA_FAIL(5001, "数据库操作失败"),
    ERROR_DATA_NOTFIND(5002, "数据不存在"),
    ERROR_DATA_NOTACCORD(5003, "数据不合规"),
    ERROR_DATA_EXISTS(5004, "数据已存在"),
    ERROR_DATA_CASCADE(5005, "数据有关联项"),

    ERROR_FILE_UPLOAD_FAIL(6001, "文件上传失败"),
    ERROR_FILE_ISEMPTY(6002, "文件为空"),
    ERROR_FILE_SIZE_EXCEEDED(6002,"文件过大"),

    ERROR_PARTIAL_FAILURE(50001,"部分操作失败");

    //状态码
    private int code;
    //状态描述
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
