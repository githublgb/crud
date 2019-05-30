package com.lgb.domain;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 4981195090220152847L;
    private String code;
    private String msg;

    private T date;

    public Response(String code ,String msg,T date){
        this.code =code;
        this.msg =msg;
        this.date =date;

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

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }
}
