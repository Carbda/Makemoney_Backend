package com.backend.makemoney.entity.vo;

import lombok.Data;

@Data
public class WorkerList {
  /** 用户Id */
  private Long userId;

  /** 用户昵称 */
  private String userNickName;

  /** 劳工等级 */
  private String workerLevel;

  /** 提现金额 */
  private String withdrawalAmount;

  /** 接单总数 */
  private String userFinishJobCount;

  /** 当前接单数量 */
  private Integer currentOngoingJobCount;

  /** 用户状态 */
  private String userStatus;
}
