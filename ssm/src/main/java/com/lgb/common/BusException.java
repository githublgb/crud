package com.lgb.common;

public class BusException  extends  RuntimeException{
   private String msg;
   private String code;

   public BusException(String code, String msg){
       super(msg);
       this.code=code;
       this.msg = msg;
   }
}
