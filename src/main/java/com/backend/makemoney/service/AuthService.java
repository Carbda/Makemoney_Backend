package com.backend.makemoney.service;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
  /** 审核订单 */
  void examinePartOrder(
      String jobId,
      String checkStatus,
      String checkPeople,
      String checkRemark,
      String percentage,
      String label);
  /** 创建审核单 */
  void createAuthOrder(String jobId, String type);

  List<HashMap> selectWithdrawal();

  void insertRealAuth(
      String userId,
      String realName,
      String cardFront,
      String cardBack,
      String cardNumber,
      String faceImage);

  List<HashMap> resultWithdraw(String userId);

  Map getWithdrawalAuthDetail(String orderId, String userId);

  void submitWithdrawalAudit(
      String orderId, String auditStatus, String auditRemark, String auditReviewer);

  /** 获取审核过的提现订单信息 */
  Map getWithdrawalAuthInformation(String orderId);

  /** 添加企业认证信息+企业认证服务单 */
  void insertCompanyAuth(
      String companyOrderId,
      String userId,
      String realName,
      String cardNumber,
      String companyName,
      String cardFront,
      String cardBack,
      String companyLicense,
      String faceImage,
      String legalCardFront,
      String legalCardBack,
      String companyStyle,
      String companyAddress,
      String legalPeople,
      String registeredCapital,
      String businessTerm,
      String incorporation,
      String businessScope);

  void updateCompanyAuth(String companyOrderId,
      String reviewer,
      String auditStatus,
       String auditRemark);
  List<HashMap> selectCompanyAuth();
  HashMap selectCompanyAuthInfo( String enterpriseOrder);
  void updateUserCompanyAuth( String userId);
  void updateUserCompanyAuth2( String userId);
}
