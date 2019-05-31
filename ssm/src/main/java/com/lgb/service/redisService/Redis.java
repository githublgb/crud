package com.lgb.service.redisService;

public  interface Redis {

      public  void set(String paramString, Object paramObject);

      public  Boolean setnx(String paramString, Object paramObject);
}
