package com.backend.makemoney.controller.common;

import com.backend.makemoney.service.CountService;
import com.backend.makemoney.utils.Result;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountController {
  @Resource private CountService countService;

  /**
   * 劳工排行
   * @param startTime
   * @param endTime
   * @return
   */
  @CrossOrigin
  @GetMapping (value = "/workerRanking")
  public Result getWorkerRanking(
      @RequestParam String startTime,
      @RequestParam String endTime) {
    return Result.success(
        "劳工排行", countService.workerRanking(startTime, endTime));
  }

  /**
   * 招聘方排行
   * @param startTime
   * @param endTime
   * @return
   */
  @CrossOrigin
  @GetMapping (value = "/employerRanking")
  public Result employerRanking(
      @RequestParam String startTime,
      @RequestParam String endTime) {
    return Result.success(
        "招聘方排行", countService.employerRanking(startTime, endTime));
  }

  @CrossOrigin
  @GetMapping (value = "/countFinishOrder7day")
  public Result countFinishOrder7days(@RequestParam String date){
    Map map=new HashMap();
    map.put("finishNum",countService.countFinishOrder7days(date));
    map.put("finishPay",countService.countFinishOrderPay7days(date));

    return Result.success("订单完成情况：订单完成数、订单交易总额",map);
  }
  @CrossOrigin
  @GetMapping (value = "/countOrderUser7days")
  public Result countOrderUser7days(@RequestParam String date){
    Map map=new HashMap();
    map.put("workerNum",countService.selectWorkerNumBeforeDay(date));
    map.put("employerNum",countService.selectEmployerNumBeforeDay(date));
    return Result.success("用户订单统计：接单人数、发单人数",map);
  }
  @CrossOrigin
  @GetMapping (value = "/countWithdrawal7days")
  public Result countWithdrawal7days(@RequestParam String date){
    Map map=new HashMap();
    map.put("withdrawal",countService.countWithdrawalBefore7Days(date));
    map.put("withdrawalNum",countService.countWithdrawalNumBefore7Days(date));
    return Result.success("提现概要",map);
  }
  @CrossOrigin
  @GetMapping (value = "/countRecharge7days")
  public Result countRecharge7days(@RequestParam String date){
    Map map=new HashMap();
    map.put("rechargeNum",countService.countRechargeNumBefore7Days(date));
    map.put("recharge",countService.countRechargeBefore7Days(date));
    return Result.success("充值概要",map);
  }
  @CrossOrigin
  @GetMapping (value = "/countUser7days")
  public Result countUser7days(@RequestParam String date){
    Map map=new HashMap();
    map.put("newUser",countService.countRegisterUserBeforeDay(date));
    map.put("newEmployer",countService.countEmployerUserBeforeDay(date));
    return Result.success("用户增长情况：新注册用户、新增企业HR",map);
  }
}
