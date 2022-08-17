package com.backend.makemoney.controller.order;

import com.backend.makemoney.entity.PartOrder;
import com.backend.makemoney.entity.Wallet;
import com.backend.makemoney.entity.vo.OrderAuthList;
import com.backend.makemoney.mapper.AuthMapper;
import com.backend.makemoney.service.OrderService;
import com.backend.makemoney.service.UserService;
import com.backend.makemoney.service.WalletService;
import com.backend.makemoney.utils.FileUploadUtils;
import com.backend.makemoney.utils.Result;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/order")
public class OrderController {
  @Resource private OrderService orderService;
  @Resource private AuthMapper authMapper;
  @Resource private WalletService walletService;

  @GetMapping(value = "/GetPartOrderbyPage")
  public List<PartOrder> getAllPartOrderbyPage(
      @RequestParam int pageNum, @RequestParam int pageSize) {
    return orderService.getAllPartOrderbyPage(pageNum, pageSize);
  }

  @GetMapping(value = "/GetAllPartOrder")
  public List<PartOrder> getAllPartOrder() {
    return orderService.getAllPartOrder();
  }

  /**
   * 订单管理-兼职订单
   *
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getOnPartOrder")
  public Result getOnPartOrder() {
    return Result.success("订单管理-兼职订单", orderService.getOnPartOrder());
  }
  /**
   * 获取兼职审核订单列表
   *
   * @param partOrderAuthList
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getPartOrderAuthList")
  public Result getPartOrderAuthList(OrderAuthList partOrderAuthList) {
    return Result.success("兼职审核订单列表", orderService.getPartOrderAuthList(partOrderAuthList));
  }

  /**
   * 获取全职审核订单列表
   *
   * @param fullOrderAuthList
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getFullOrderAuthList")
  public Result getFullOrderAuthList(OrderAuthList fullOrderAuthList) {
    return Result.success("全职审核订单列表", orderService.getFullOrderAuthList(fullOrderAuthList));
  }

  /**
   * 创建兼职单
   *
   * @param userId
   * @param title
   * @param description
   * @param descriptionRemark
   * @param workerNum
   * @param sexNeed
   * @param heightNeed
   * @param ageNeed
   * @param workStartTime
   * @param workEndTime
   * @param gatherTime
   * @param salary
   * @param leaderNum
   * @param verifyNeed
   * @param location
   * @param leaderDescription
   * @param file
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/createPartOrder")
  public Result createPartOrder(
      @RequestParam("userId") String userId,
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("descriptionRemark") String descriptionRemark,
      @RequestParam("workerNum") Integer workerNum,
      @RequestParam("sexNeed") String sexNeed,
      @RequestParam("heightNeed") String heightNeed,
      @RequestParam("ageNeed") String ageNeed,
      @RequestParam("workStartTime") String workStartTime,
      @RequestParam("workEndTime") String workEndTime,
      @RequestParam("gatherTime") String gatherTime,
      @RequestParam("salary") Double salary,
      @RequestParam("leaderNum") Integer leaderNum,
      @RequestParam("verifyNeed") Integer verifyNeed,
      @RequestParam("location") String location,
      @RequestParam("leaderDescription") String leaderDescription,
      @RequestParam("leaderWages") Double leaderWages,
      @RequestParam("file") MultipartFile file) {
    PartOrder partOrder = new PartOrder();
    Date date = new Date();
    SimpleDateFormat idFormat = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    partOrder.setStatus(0);
    partOrder.setUserId(userId);
    partOrder.setName(title);
    partOrder.setAgeRequirement(ageNeed);
    partOrder.setWorkStartTime(workStartTime);
    partOrder.setWorkEndTime(workEndTime);
    partOrder.setGatherTime(gatherTime);
    partOrder.setWages(salary);
    partOrder.setTeamLeaderNumber(leaderNum);
    partOrder.setIsExammine(verifyNeed);
    partOrder.setTeamLeaderRequirement(leaderDescription);
    partOrder.setDescribe(description);
    partOrder.setRemark(descriptionRemark);
    partOrder.setAddress(location);
    partOrder.setCreateTime(timeFormat.format(date));
    partOrder.setRecruitsNumber(workerNum);
    partOrder.setAgeRequirement(ageNeed);
    partOrder.setSexRequirement(sexNeed);
    partOrder.setHeightRequirement(heightNeed);
    partOrder.setTeamLeaderWages(leaderWages);
    String jobId = idFormat.format(date) + partOrder.getUserId().substring(1); // 根据时间和用户ID前两位生成订单ID
    partOrder.setJobId(jobId);
    String url = "";
    if (file != null) {
      String baseDir = "/MakeMoney/upload/workPictures/" + jobId + "/";
      try {
        url =
            "http://47.113.217.175:8080/pictures/workPictures/"
                + jobId
                + "/"
                + FileUploadUtils.upload(baseDir, file);
      } catch (IOException e) {
        e.printStackTrace();
        return Result.error(500, "上传图片过程出错！");
      }
    }
    partOrder.setEnvironmentImg(url);
    orderService.createPartOrder(partOrder);
    authMapper.insertAuthOrder(partOrder.getJobId(), "0");
    //  logService.addLog((String) map.get("userId"),"订单","创建兼职订单",'1');
    return Result.success("创建兼职订单", partOrder);
  }

  /**
   * 获取招聘方首页数据
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/employerIndex")
  public Result getEmployerIndex(@RequestParam String userId) {
    return Result.success("招聘方首页数据", orderService.getEmployerIndex(userId));
  }

  /**
   * 获取劳工首页数据
   *
   * @param pageNum
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/workerIndex")
  public Result getworkerIndex(@RequestParam Integer pageNum) {
    return Result.success("劳工首页数据", orderService.getWorkerIndex(pageNum));
  }

  /**
   * 获取兼职单详情+订单推荐+用户报名信息
   *
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/{jobId}")
  public Result getpartOrderInfo(
      @PathVariable("jobId") String jobId,
      @RequestParam String label,
      @RequestParam Integer pageNum,
      @RequestParam String userId) {
    return Result.success(
        "兼职招聘单详情+订单推荐+用户报名信息", orderService.getPartOrderInfo(jobId, label, pageNum, userId));
  }

  /**
   * 获取审核兼职订单详情
   *
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/authPartOrder/{jobId}")
  public Result getauthPartOrder(@PathVariable("jobId") String jobId) {
    return Result.success("审核兼职订单信息", orderService.getAuthPartOrder(jobId));
  }

  /**
   * 劳工方工作台
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/workerOrder/{userId}")
  public Result getworkerOrder(@PathVariable("userId") String userId) {
    return Result.success("劳工工作台", orderService.getWorkerOrder(userId));
  }

  /**
   * 招聘方工作台
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/employerOrder/{userId}")
  public Result getemployerOrder(@PathVariable("userId") String userId) {
    return Result.success("招聘方工作台", orderService.getEmployerOrder(userId));
  }

  /**
   * 用户报名
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/apply")
  public Result payDeposit(@RequestBody Map map) {

    String userId = (String) map.get("userId");
    String jobId = (String) map.get("jobId");
    String type = (String) map.get("type");
    HashMap order = orderService.selectTaskPartOrderByJobId(jobId);
    int leaderNum= (int) order.get("leaderNum");
    int leaderNeed= (int) order.get("leaderNeed");
    if (order == null) return Result.error(500, "订单" + jobId + "报名已结束！");
    if(type.equals("2") && leaderNum>=leaderNeed){
      return Result.error(500,"领队人数已满，报名失败！");
    }
    Wallet wallet = walletService.selectWallet((String) map.get("userId"));
    if (wallet == null) return Result.error(505, "该用户钱包异常");
    Double amountBefore = wallet.getTotalAmount();
    Double amountAfter = amountBefore - 50;
    String remark = "报名押金";
    if (!wallet.getPassWord().equals(map.get("passWord"))) {
      return Result.error(500, "支付密码错误");
    } else if (wallet.getTotalAmount() < 50) {
      return Result.error(501, "余额不足");
    }
    orderService.addPartOrderApplyNum(jobId);
    try {
      if (orderService.selectPartOrder(jobId).getIsExammine() == 0) { // 订单不用审核
        orderService.insertJobApply(userId, jobId, type, "1", "无需审核");
        orderService.addPartOrderWorkerNum(jobId);
        orderService.updateWorkerApply(jobId,userId,2);  // 进行中
        if(type.equals("2")) orderService.addPartOrderLeaderNum(jobId);
      } else { // 订单需要审核
        orderService.insertJobApply(userId, jobId, type, "0", null);
      }
    } catch (Exception e) {
      return Result.error(502, "不能重复报名");
    }
    walletService.payDeposit(userId, jobId, 5, amountBefore, amountAfter, 50.00, remark);
    // logService.addLog(userId,"订单","报名兼职订单",'1');
    return Result.success("报名成功", orderService.selectUserApply(jobId, userId));
  }

  /**
   * 下架订单
   *
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/deletePartOrder")
  public Result closePartOrder(@RequestParam String jobId) {
    orderService.closePartOrder(jobId);
    return Result.success("下架订单");
  }

  /**
   * 取消报名订单
   *
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/cancelPartOrder")
  public Result cancelPartOrder(@RequestParam String jobId, @RequestParam String userId) {

    HashMap userApply=orderService.selectUserApply(jobId,userId);
    if(userApply.get("auditStatus").equals("1")) orderService.reducePartOrderWorkerNum(jobId);
    orderService.cancelOrder(userId, jobId);
    return Result.success("用户" + userId + "取消报名订单" + jobId);
  }

  /**
   * 录取劳工
   *
   * @param jobId
   * @param userId
   * @param status
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/hireWorker")
  public Result hireWorker(
      @RequestParam String jobId, @RequestParam String userId, @RequestParam String status) {
    orderService.hireWorker(userId, jobId, status);
    if (status.equals("1")) return Result.success("订单[" + jobId + "]：录用劳工[" + userId + "]成功！");
    else return Result.success("订单[" + jobId + "]：不录用劳工[" + userId + "]！");
  }

  /**
   * 获取订单报名情况
   *
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/selectOrderApply")
  public Result selectOrderApply(@RequestParam String jobId) {
    return Result.success("订单【" + jobId + "】报名列表", orderService.selectOrderApply(jobId));
  }

  /**
   * web订单跟踪，订单报名信息
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/selectOrderApplyInfo")
  public Result selectOrderApplyInfo(@RequestParam String jobId) {
    return Result.success("订单跟踪：订单【" + jobId + "】报名信息", orderService.selectOrderApplyInfo(jobId));
  }

  /**
   * web跟踪订单，订单交易信息
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/selectOrderPayInfo")
  public Result selectOrderPayInfo(@RequestParam String jobId) {
    return Result.success("订单【" + jobId + "】交易信息", orderService.selectOrderPayInfo(jobId));
  }

  /**
   * web订单跟踪，订单劳工信息
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/selectOrderWorkerInfo")
  public Result selectOrderWorkerInfo(@RequestParam String jobId) {
    return Result.success("订单【" + jobId + "】劳工信息", orderService.selectOrderWorkerInfo(jobId));
  }

  /**
   * web跟踪订单，订单信息
   * @param jobId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/selectOrderHeader")
  public Result selectOrderHeader(@RequestParam String jobId) {
    return Result.success("订单【" + jobId + "】基本信息", orderService.selectOrderHeader(jobId));
  }

  @CrossOrigin
  @GetMapping(value = "/selectJobs")
  public List<HashMap> selectJobs(@RequestParam("jobName") String jobName) {
    return orderService.selectJobs(jobName);
  }
  @CrossOrigin
  @GetMapping(value = "/selectMyJobs")
  public List<HashMap> selectMyJobs(@RequestParam("jobName") String jobName,@RequestParam("userId") String userId) {
    return orderService.selectMyJobs(jobName, userId);
  }
}
