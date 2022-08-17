package com.backend.makemoney.mapper;

import java.util.List;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {

  /** 审核兼职订单操作 */
  void examinePartOrder(
      String jobId,
      String checkStatus,
      String checkPeople,
      String checkRemark,
      String percentage,
      String label);

  /** 创建审核订单 */
  void insertAuthOrder(String jobId, String type);

  /** 获取提现申请列表 */
  List<HashMap> selectWithdrawal();

  /** 用户实名认证 */
  void insertRealAuth(
      @Param("userId") String userId,
      @Param("realName") String realName,
      @Param("cardFront") String cardFront,
      @Param("cardBack") String cardBack,
      @Param("cardNumber") String cardNumber,
      @Param("faceImage") String faceImage);

  List<HashMap> resultWithdraw(String userId);

  void updateRealAuth(String userId);

  List<HashMap> recentCapitalFlowById(@Param("userId") String userId);

  HashMap selectWithdrawalById(String orderId);

  void submitWithdrawalAudit(
      @Param("orderId") String orderId,
      @Param("auditStatus") String auditStatus,
      @Param("auditRemark") String auditRemark,
      @Param("auditReviewer") String auditReviewer);

  HashMap selectAuthInfomation(String orderId);

  void insertCompanyInfo(
      @Param("companyOrderId") String companyOrderId,
      @Param("userId") String userId,
      @Param("realName") String realName,
      @Param("cardNumber") String cardNumber,
      @Param("companyName") String companyName,
      @Param("cardFront") String cardFront,
      @Param("cardBack") String cardBack,
      @Param("companyLicense") String companyLicense,
      @Param("faceImage") String faceImage,
      @Param("legalCardFront") String legalCardFront,
      @Param("legalCardBack") String legalCardBack,
      @Param("companyStyle") String companyStyle,
      @Param("companyAddress") String companyAddress,
      @Param("legalPeople") String legalPeople,
      @Param("registeredCapital") String registeredCapital,
      @Param("businessTerm") String businessTerm,
      @Param("incorporation") String incorporation,
      @Param("businessScope") String businessScope
      );
  void insertCompanyAuth(
      @Param("companyOrderId") String companyOrderId
  );
  void updateCompanyAuth(
      @Param("companyOrderId") String companyOrderId,
      @Param("reviewer") String reviewer,
      @Param("auditStatus") String auditStatus,
      @Param("auditRemark") String auditRemark
  );
  List<HashMap> selectCompanyAuth();
  HashMap selectCompanyAuthInfo( @Param("enterpriseOrder") String enterpriseOrder);
  void updateUserCompanyAuth(@Param("userId") String userId);
  void updateUserCompanyAuth2(@Param("userId") String userId);
}
