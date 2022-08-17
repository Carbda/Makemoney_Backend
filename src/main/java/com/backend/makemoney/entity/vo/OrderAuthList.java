package com.backend.makemoney.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class OrderAuthList {

  /** 订单ID */
  private Long jobId;

  /** 职位名称 */
  private String name;

  /** 创建时间 */
  @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date createTime;

  /** 发单人身份 */
  private Long userType;

  /** 招募人数 */
  private Long recruitsNumber;

  /** 工资 */
  private BigDecimal wages;


}
