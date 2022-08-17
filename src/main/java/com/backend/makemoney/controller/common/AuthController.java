package com.backend.makemoney.controller.common;

import com.backend.makemoney.service.AuthService;
import com.backend.makemoney.utils.FileUploadUtils;
import com.backend.makemoney.utils.Result;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@RestController()
public class AuthController {
  @Resource private AuthService authService;

  /**
   * 审核兼职订单
   *
   * @param jobId
   * @param checkStatus
   * @param checkPeople
   * @param checkRemark
   * @param percentage
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/partOrder")
  public Result examinePartOrder(
      @RequestParam String jobId,
      @RequestParam String checkStatus,
      @RequestParam String checkPeople,
      @RequestParam String checkRemark,
      @RequestParam String percentage,
      @RequestParam String label) {
    authService.examinePartOrder(jobId, checkStatus, checkPeople, checkRemark, percentage, label);
    return Result.success("审核兼职订单");
  }

  /**
   * 获取提现申请列表
   *
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getWithdrawal")
  public Result getWithdrawal() {
    return Result.success("获取提现申请列表", authService.selectWithdrawal());
  }

  @CrossOrigin
  @PostMapping(value = "/realAuth")
  public Result realAuth(
      String userId, String realName, String cardNumber, MultipartRequest files) {

    String baseDir = "/MakeMoney/upload/authPictures/" + userId + "/";
    MultipartFile cardFront = files.getFile("image0");
    MultipartFile cardBack = files.getFile("image1");
    MultipartFile faceImage = files.getFile("image2");
    try {
      String cardFrontUrl =
          "http://47.113.217.175:8080/pictures/authPictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, cardFront);
      String cardBackUrl =
          "http://47.113.217.175:8080/pictures/authPictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, cardBack);
      String faceImageUrl =
          "http://47.113.217.175:8080/pictures/authPictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, faceImage);
      authService.insertRealAuth(
          userId, realName, cardFrontUrl, cardBackUrl, cardNumber, faceImageUrl);
      return Result.success("提交实名认证成功");
    } catch (IOException e) {
      e.printStackTrace();
      return Result.error(500, "上传图片失败！");
    }
  }

  /**
   * 用户获取提现结果列表
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getWithdrawalResult")
  public Result resultWithdrawal(@RequestParam String userId) {
    return Result.success("获取用户[" + userId + "]提现结果列表", authService.resultWithdraw(userId));
  }

  /**
   * 获取用户提现申请对账信息，提现单信息、钱包统计、流水账单
   *
   * @param userId
   * @param orderId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getWithdrawalDetail")
  public Result getWithdrawalDetail(@RequestParam String userId, @RequestParam String orderId) {
    return Result.success(
        "服务单[" + orderId + "]:用户[" + userId + "]提现对账信息",
        authService.getWithdrawalAuthDetail(orderId, userId));
  }

  /**
   * 审核提现服务单操作
   *
   * @param orderId
   * @param auditStatus
   * @param auditRemark
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/authWithdrawal")
  public Result authWithdrawal(
      @RequestParam String orderId,
      @RequestParam String auditStatus,
      @RequestParam String auditRemark,
      @RequestParam String auditReviewer) {
    authService.submitWithdrawalAudit(orderId, auditStatus, auditRemark, auditReviewer);
    if (auditStatus.equals("1")) return Result.success("服务单[" + orderId + "]提现申请通过！");
    else return Result.success("服务单[" + orderId + "]提现申请不通过！");
  }

  /**
   * 获取审核过的提现单信息
   *
   * @param orderId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getWithdrawalAuthDetail")
  public Result getWithdrawalAuthDetail(@RequestParam String orderId) {
    return Result.success(
        "服务单[" + orderId + "]提现审核信息", authService.getWithdrawalAuthInformation(orderId));
  }


  /**
   * 发起企业认证申请
    * @param userId
   * @param realName
   * @param cardNumber
   * @param companyName
   * @param companyStyle
   * @param companyAddress
   * @param legalPeople
   * @param registeredCapital
   * @param businessTerm
   * @param incorporation
   * @param businessScope
   * @param files
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/addCompanyAuth")
  public Result addCompanyAuth(
      String userId,
      String realName,
      String cardNumber,
      String companyName,
      String companyStyle,
      String companyAddress,
      String legalPeople,
      String registeredCapital,
      String businessTerm,
      String incorporation,
      String businessScope,
      MultipartRequest files) {
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("yyyyMMddHHmmss");
    Date date = new Date();
    String companyOrderId = sdf.format(date);
    String baseDir = "/MakeMoney/upload/authEnterprisePictures/" + userId + "/";
    MultipartFile cardFront = files.getFile("image0");
    MultipartFile cardBack = files.getFile("image1");
    MultipartFile faceImage = files.getFile("image2");
    MultipartFile card2Front = files.getFile("image3");
    MultipartFile card2Back = files.getFile("image4");
    MultipartFile companyLicense = files.getFile("image5");
    try {
      String cardFrontUrl =
          "http://47.113.217.175:8080/pictures/authEnterprisePictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, cardFront);
      String cardBackUrl =
          "http://47.113.217.175:8080/pictures/authEnterprisePictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, cardBack);
      String faceImageUrl =
          "http://47.113.217.175:8080/pictures/authEnterprisePictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, faceImage);
      String card2FrontUrl =
          "http://47.113.217.175:8080/pictures/authEnterprisePictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, card2Front);
      String card2BackUrl =
          "http://47.113.217.175:8080/pictures/authEnterprisePictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, card2Back);
      String companyLicenseUrl =
          "http://47.113.217.175:8080/pictures/authEnterprisePictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, companyLicense);
      authService.insertCompanyAuth(
          companyOrderId,
          userId,
          realName,
          cardNumber,
          companyName,
          cardFrontUrl,
          cardBackUrl,
          companyLicenseUrl,
          faceImageUrl,
          card2FrontUrl,
          card2BackUrl,
          companyStyle,
          companyAddress,
          legalPeople,
          registeredCapital,
          businessTerm,
          incorporation,
          businessScope);
      authService.updateUserCompanyAuth2(userId);
      return Result.success("用户【" + userId + "】发起企业认证申请，服务单号为：【" + companyOrderId + "】");
    } catch (IOException e) {
      e.printStackTrace();
      return Result.error(500,"上传图片失败！");
  }
    }

  @CrossOrigin
  @PostMapping(value = "/authCompany")
  public Result authCompany(@RequestBody Map map) {
    String userId= (String) map.get("userId");
    String companyOrderId = (String) map.get("enterpriseOrderId");
    String reviewer = (String) map.get("reviewer");
    String auditStatus = (String) map.get("auditStatus");
    String auditRemark = (String) map.get("auditRemark");
    authService.updateCompanyAuth(companyOrderId,reviewer,auditStatus,auditRemark);
    if (auditStatus.equals("1")) {
      authService.updateUserCompanyAuth(userId);
      return Result.success("服务单号【" + companyOrderId + "】 认证成功!");
    }
    else return Result.success("服务单号【" + companyOrderId + "】 认证失败!");
  }
  @CrossOrigin
  @GetMapping(value = "/getAuthCompany")
  public Result getAuthCompany() {
    return Result.success("获取企业认证申请列表",authService.selectCompanyAuth());
  }
  @CrossOrigin
  @GetMapping(value = "/getAuthCompanyInfo")
  public Result getAuthCompanyInfo(@RequestParam("enterpriseOrder") String enterpriseOrder) {
    return Result.success("获取申请认证的企业信息,服务单【"+enterpriseOrder+"】",authService.selectCompanyAuthInfo(enterpriseOrder));
  }
}
