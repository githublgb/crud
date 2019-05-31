package com.lgb.bootweb.common;


import com.lgb.bootweb.Enum.ExceptionEnum;

public class ServiceException extends  RuntimeException{
    //异常编号
    private String exCode;

    //异常信息
    private String exMsg;

    public ServiceException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.exCode = exceptionEnum.getCode();
        this.exMsg= exceptionEnum.getMsg();
    }

    private ServiceException(String exCode, String exMsg , Throwable cause){
        super(exMsg,cause);
        this.exCode=exCode;
        this.exMsg= exMsg;
    };

    private ServiceException(String exCode, String exMsg){
        super(exMsg);
        this.exCode = exCode;
        this.exMsg= exMsg;
    };

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }
}
