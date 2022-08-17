package com.backend.makemoney.entity.vo;

import lombok.Data;

@Data
public class EmployerList {
  /** 用户ID */
  private Long userId;

  /** 用户类型：0普通用户 1劳工 2企业HR */
  private String userType;

  /** 用户昵称 */
  private String userNickName;

  /** 帐号状态 */
  private String userStatus;

  /** 招聘方等级 */
  private String bossGrowthValue;

  /** 发单总数 */
  private Long totalJobOrder;

  /** 当前接单数 */
  private Long userCurrentOngoingJobCount;

  /** 结账总额 */
  private Long totalPay;
}
