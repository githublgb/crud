package com.lgb.bootweb.Enum;

public enum OptStatusEnum {
    ADD("01", "增加"),
    UPDATE("02", "修改"),
    DELETE("03", "删除");

    private String code;
    private String msg;

    private OptStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValueByKey(String code) {
        for (OptStatusEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.msg;
            }
        }
        return null;
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
