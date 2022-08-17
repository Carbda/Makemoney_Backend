package com.backend.makemoney.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class PartOrderList {

  /** $column.columnComment */
  private Long id;

  /** 兼职单子ID */
  private Long jobId;

  /** 发单人ID */
  private Long userId;

  /** 职位名称 */
  private String name;

  /** 招募人数 */
  private Long recruitsNumber;

  /** 性别要求 */
  private String sexRequirement;

  /** 身高要求 */
  private String heightRequirement;

  /** 年龄要求 */
  private String ageRequirement;

  /** 开始时间 */
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date workStartTime;

  /** 结束时间 */
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date workEndTime;

  /** 集合时间 */
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date gatherTime;

  /** 工资 */
  private BigDecimal wages;

  /** 领队数量 */
  private Long teamLeaderNumber;

  /** 报名是否审核（0：不需要 1：需要） */
  private Long isExammine;

  /** 领队要求 */
  private String teamLeaderRequirement;

  /** 工作描述 */
  private String describe;

  /** 工作环境照片 */
  private String environmentImg;

  /** 群聊ID */
  private Long groupId;

  /** 订单状态 */
  private Long status;

  /** 订单总金额 */
  private BigDecimal amount;

  /** 报名人数 */
  private Long application;

  /** 浏览数量 */
  private Long watchNumber;

  /** 劳工数量 */
  private Long workerNumber;

  /** 领队数量 */
  private Long totalTeamLeaderNumber;

  /** 领队工资 */
  private BigDecimal teamLeaderWages;

  /** 地址 */
  private String address;

  /** 标签 */
  private String label;

}
