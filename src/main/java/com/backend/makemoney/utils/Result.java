package com.backend.makemoney.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result  {
  // 返回标志的状态码
  private Integer code;
  // 返回消息
  private String message;
  // 返回的数据类型
  private Object data;

  public static Result success(String msg) {
    return Result.success(msg, null);
  }
  public static Result success(Object data)
  {
    return Result.success(HttpStatusEnum.SUCCESS.getMessage(),data);
  }
  public static Result success(String msg, Object data) {
    return new Result(HttpStatusEnum.SUCCESS.getCode(), msg, data);
  }
  public static Result error(Integer code , String msg){
    return new Result(code,msg,null);
  }
}
