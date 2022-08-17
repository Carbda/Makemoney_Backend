package com.backend.makemoney.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class User {
  /** 用户ID */
  private Long userId;

  /** 用户手机号 */
  private String userPhone;

  /** 密码 */
  private String userPwd;

  /** 用户注册时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date userCreateTime;

  /** 用户钱包ID */
  private Long userWalletId;

  /** 用户类型：0普通用户 1企业HR */
  private Long userType;

  /** 用户昵称 */
  private String userNickName;

  /** 微信号 */
  private String userWechat;

  /** 用户头像 */
  private String userAvatar;

  /** 帐号状态 */
  private String userStatus;

  /** 最后一次登录IP */
  private String userLoginIp;

  /** 最后一次登陆时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date loginTime;

  /** 劳工-成长值 */
  private Long workerGrowthValue;

  /** 招聘方-成长值 */
  private Long bossGrowthValue;

  /** 全职权限 */
  private String fullTimeAuth;

  /** 领队资格（0：没有 1：有） */
  private Integer teamLeaderCertification;

  /** 关注数 */
  private Long totalFollowCount;

  /** 粉丝数 */
  private Long totalFansCount;

  /** 总发单数量 */
  private Long totalJobOrder;

  /** 是否填写简历 */
  private Integer userResumeStatus;

  /** 是否实名认证 */
  private Integer userRealNameAuth;

  /** 是否企业认证 */
  private Integer userEnterpriseCertification;

  /** 接单完成数 */
  private Long userFinishJobCount;

  /** 当前接单数 */
  private Long userCurrentOngoingJobCount;

  /** 更新时间 */
  private Date userUpdateTime;
}
