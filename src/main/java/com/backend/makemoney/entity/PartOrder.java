package com.backend.makemoney.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class PartOrder {

  /** $column.columnComment */
  private Long id;

  /** 兼职单子ID */
  private String jobId;

  /** 发单人ID */
  private String userId;

  /** 职位名称 */
  private String name;

  /** 招募人数 */
  private Integer recruitsNumber;

  /** 性别要求 */
  private String sexRequirement;

  /** 身高要求 */
  private String heightRequirement;

  /** 年龄要求 */
  private String ageRequirement;

  /** 开始时间 */
  private String workStartTime;

  /** 结束时间 */
  private String workEndTime;

  /** 集合时间 */
  private String gatherTime;

  /** 工资 */
  private Double wages;

  /** 领队数量 */
  private Integer teamLeaderNumber;

  /** 报名是否审核（0：不需要 1：需要） */
  private Integer isExammine;

  /** 领队要求 */
  private String teamLeaderRequirement;

  /** 工作描述 */
  private String describe;

  /** 工作环境照片 */
  private String environmentImg;

  /** 群聊ID */
  private Long groupId;

  /** 订单状态 */
  private int status;

  /** 订单总金额 */
  private Double amount;

  /** 报名人数 */
  private Integer application;

  /** 浏览数量 */
  private Integer watchNumber;

  /** 劳工数量 */
  private Integer workerNumber;

  /** 订单备注 */
  private String remark;

  /** 领队数量 */
  private Integer totalTeamLeaderNumber;

  /** 领队工资 */
  private Double teamLeaderWages;

  /** 地址 */
  private String address;

  /** 标签 */
  private String label;

  /** 创建时间 */
  private String createTime;

  /** 更新时间 */
  private String updateTime;
}
