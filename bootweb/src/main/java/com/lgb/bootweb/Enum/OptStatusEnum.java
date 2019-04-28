package com.lgb.bootweb.Enum;

public enum  OptStatusEnum {
    ADD("01", "添加"),
    UPDATE("02", "修改"),
    DELETE("03", "删除"),
    SELECT("04","查询");

    private String key;
    private String value;

    private OptStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (OptStatusEnum statusEnum : values()) {
            if (statusEnum.key.equals(key)) {
                return statusEnum.value;
            }
        }
        return null;
    }

    public String getKey()
    {
        return this.key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
