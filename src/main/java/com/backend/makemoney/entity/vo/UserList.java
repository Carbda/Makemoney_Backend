package com.backend.makemoney.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class UserList {
  /** 用户ID */
  private Long userId;

  /** 用户手机号 */
  private String userPhone;

  /** 用户类型 */
  private Integer userType;

  /** 用户状态 */
  private String userStatus;

  /** 用户昵称 */
  private String userNickName;

  /** 账号创建时间 */
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date userCreateTime;

  /** 最后一次登录时间 */
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date loginTime;
}
