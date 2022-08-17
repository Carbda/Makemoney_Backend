package com.backend.makemoney.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Resume {
  /** 用户ID */
  private String userId;
  /** 真实姓名 */
  private String realName;
  /** 性别 */
  private Integer sex;
  /** 年龄 */
  private Integer age;
  /** 电话号码 */
  private String phone;
  /** 身高 */
  private String height;
  /** 学历 */
  private String education;
  /** 政治面貌 */
  private String politicsStatus;
  /** 民族 */
  private String nation;
  /** 个人照片 */
  private String photo;
  /** 工作经历 */
  private String workExperience;
  /** 个人优势 */
  private String advantages;
  /** 创建时间 */
  private String createTime;
  /** 更新时间 */
  private String updateTime;
}
