package com.backend.makemoney.utils;

public enum HttpStatusEnum {
  SUCCESS(200, "操作成功"),
  USER_LOGIN_LOSS(201, "用户未注册"),
  USER_LOGIN_FAIL(202, "输入账号或密码错误"),
  USER_CODE_FAIL(203, "验证码错误"),
  USER_CODE_LOSS(206, "验证码已过期"),
  USER_REGISTER_REPEAT(204, "手机号已被注册"),
  USER_GETUSER_FAIL(205, "获取用户信息失败"),
  USER_FIND_LOSS (208,"用户不存在");
  private final int code;
  private final String message;

  HttpStatusEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
