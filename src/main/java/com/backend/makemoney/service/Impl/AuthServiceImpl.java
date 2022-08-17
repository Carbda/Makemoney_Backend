package com.backend.makemoney.service.Impl;

import com.backend.makemoney.mapper.AuthMapper;
import com.backend.makemoney.mapper.WalletMapper;
import com.backend.makemoney.service.AuthService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  @Resource private AuthMapper authMapper;
  @Resource private WalletMapper walletMapper;
  /** 审核兼职订单 */
  @Override
  public void examinePartOrder(
      String jobId,
      String checkStatus,
      String checkPeople,
      String checkRemark,
      String percentage,
      String label) {
    authMapper.examinePartOrder(jobId, checkStatus, checkPeople, checkRemark, percentage, label);
  }

  /** 创建审核单 */
  @Override
  public void createAuthOrder(String jobId, String type) {
    authMapper.insertAuthOrder(jobId, type);
  }

  /** 获取提现申请单 */
  @Override
  public List<HashMap> selectWithdrawal() {
    return authMapper.selectWithdrawal();
  }

  /**
   * 用户实名认证
   *
   * @param userId
   * @param realName
   * @param cardFront
   * @param cardBack
   * @param cardNumber
   * @param faceImage
   */
  @Override
  public void insertRealAuth(
      String userId,
      String realName,
      String cardFront,
      String cardBack,
      String cardNumber,
      String faceImage) {
    authMapper.insertRealAuth(userId, realName, cardFront, cardBack, cardNumber, faceImage);
    authMapper.updateRealAuth(userId);
  }

  /**
   * 提现申请结果
   *
   * @param userId
   */
  @Override
  public List<HashMap> resultWithdraw(String userId) {
    return authMapper.resultWithdraw(userId);
  }

  @Override
  public Map getWithdrawalAuthDetail(String orderId, String userId) {
    Map map = new HashMap();
    map.put("walletAmount", walletMapper.getWalletAmount(userId));
    map.put("withdrawalDetail", authMapper.selectWithdrawalById(orderId));
    map.put("recentCapitalFlow", authMapper.recentCapitalFlowById(userId));
    return map;
  }

  /**
   * 审核提现申请
   *
   * @param orderId
   * @param auditStatus
   * @param auditRemark
   * @param auditReviewer
   */
  @Override
  public void submitWithdrawalAudit(
      String orderId, String auditStatus, String auditRemark, String auditReviewer) {
    authMapper.submitWithdrawalAudit(orderId, auditStatus, auditRemark, auditReviewer);
  }

  /**
   * 获取审核过的提现单信息
   *
   * @param orderId
   * @return
   */
  @Override
  public Map getWithdrawalAuthInformation(String orderId) {
    Map map = new HashMap();
    map.put("withdrawlDetail", authMapper.selectWithdrawalById(orderId));
    map.put("authDetail", authMapper.selectAuthInfomation(orderId));
    return map;
  }

  /**
   * 添加企业认证信息+企业认证服务单
   *
   * @param userId
   * @param realName
   * @param cardNumber
   * @param companyName
   * @param cardFront
   * @param cardBack
   * @param companyLicense
   * @param faceImage
   * @param legalCardFront
   * @param legalCardBack
   * @param companyStyle
   * @param companyAddress
   * @param legalPeople
   * @param registeredCapital
   * @param businessTerm
   * @param incorporation
   * @param businessScope
   */
  @Override
  public void insertCompanyAuth(
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
      String businessScope) {
    authMapper.insertCompanyInfo(
        companyOrderId,
        userId,
        realName,
        cardNumber,
        companyName,
        cardFront,
        cardBack,
        companyLicense,
        faceImage,
        legalCardFront,
        legalCardBack,
        companyStyle,
        companyAddress,
        legalPeople,
        registeredCapital,
        businessTerm,
        incorporation,
        businessScope);
    authMapper.insertCompanyAuth(companyOrderId);
  }

  /**
   * 审核企业认证
   * @param companyOrderId
   * @param reviewer
   * @param auditStatus
   * @param auditRemark
   */
  @Override
  public void updateCompanyAuth(String companyOrderId, String reviewer, String auditStatus,
      String auditRemark) {
    authMapper.updateCompanyAuth(companyOrderId, reviewer, auditStatus, auditRemark);
  }

  @Override
  public List<HashMap> selectCompanyAuth() {
    return authMapper.selectCompanyAuth();
  }

  @Override
  public HashMap selectCompanyAuthInfo(String enterpriseOrder) {
    return authMapper.selectCompanyAuthInfo(enterpriseOrder);
  }

  @Override
  public void updateUserCompanyAuth(String userId) {
    authMapper.updateUserCompanyAuth(userId);
  }

  @Override
  public void updateUserCompanyAuth2(String userId) {
    authMapper.updateUserCompanyAuth2(userId);
  }
}
