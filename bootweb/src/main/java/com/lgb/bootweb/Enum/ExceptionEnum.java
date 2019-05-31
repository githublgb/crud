package com.lgb.bootweb.Enum;

public enum ExceptionEnum {
    NO_PERSON("1", "此人员不存在"),
    NAME_PERSON("2", "此人姓名不对"),
    ADDRESS_PERSON("3", "此人员地址错误"),
    PHONE_PERSON("4", "此人手机号错误");

    private String code;
    private String msg;

   private ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
