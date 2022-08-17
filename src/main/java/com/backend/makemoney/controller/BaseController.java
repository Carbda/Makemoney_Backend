package com.backend.makemoney.controller;

import com.backend.makemoney.utils.HttpStatusEnum;
import com.backend.makemoney.utils.Result;

public class BaseController {
    // 返回标志的状态码
    private Integer code;
    // 返回消息
    private String message;
    // 返回的数据类型
    private Object data;

    public static com.backend.makemoney.utils.Result success(String msg) {
      return com.backend.makemoney.utils.Result.success(msg, null);
    }
    public static com.backend.makemoney.utils.Result success(Object data)
    {
      return com.backend.makemoney.utils.Result.success(HttpStatusEnum.SUCCESS.getMessage(),data);
    }
    public static com.backend.makemoney.utils.Result success(String msg, Object data) {
      return new com.backend.makemoney.utils.Result(HttpStatusEnum.SUCCESS.getCode(), msg, data);
    }
    public static com.backend.makemoney.utils.Result error(Integer code , String msg){
      return new com.backend.makemoney.utils.Result(code,msg,null);
  }
}
