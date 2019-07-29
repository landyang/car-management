package com.example.enums;

/**
 * 聚合数据全国车辆违章查询error_code
 * @author HaN
 * @create 2019-05-12 17:50
 */
public enum JuheErrorCode {
    SUCCESS("0", "请求成功"),
    ERROR_CODE_203603("203603","网络错误请重试(不计费)"),
    ERROR_CODE_203606("203606","车辆信息错误,请确认输入的信息正确或参数错误等相关错误,以实际传输为准(请求校验后)(计费)"),
    ERROR_CODE_203607("203607","车辆信息错误：车架号发动机号车牌号错误(车辆信息错误，不扣费)"),
    ERROR_CODE_203608("203608","您好,你所查询的城市正在维护或未开通查询(如此城市维护或下线，不扣费)"),
    ERROR_CODE_203609("203609","内部错误,具体看返回(不计费)"),
    ERROR_CODE_203611("203611","根据车牌前缀获取查询规则相关错误(不计费)"),
    SYSTEM_ERROR_10001("10001","错误的请求KEY"),
    SYSTEM_ERROR_10002("10002","该KEY无请求权限"),
    SYSTEM_ERROR_10003("10003","KEY过期"),

    SYSTEM_ERROR_10012("10012","请求超过次数限制");

    private String code;
    private String message;

    JuheErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(String name) {
        for (JuheErrorCode item : JuheErrorCode.values()) {
            if (name.equals(item.name())) {
                return item.message;
            }
        }
        return null;
    }

    public static String getCode(String name) {
        for (JuheErrorCode item : JuheErrorCode.values()) {
            if (name.equals(item.name())) {
                return item.code;
            }
        }
        return null;
    }

    public static JuheErrorCode getJuheErrorCode(String code) {
        for (JuheErrorCode item : JuheErrorCode.values()) {
            if (code.equals(item.code)) {
                return item;
            }
        }
        return null;
    }
}
