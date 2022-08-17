package com.backend.makemoney.controller;

import com.backend.makemoney.entity.Wallet;
import com.backend.makemoney.mapper.OrderMapper;
import com.backend.makemoney.service.OrderService;
import com.backend.makemoney.service.UserService;
import com.backend.makemoney.service.WalletService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
public class Task {
  @Resource private OrderService orderService;
  @Resource private OrderMapper orderMapper;
  @Resource private WalletService walletService;
  @Resource private UserService userService;
  private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Scheduled(cron = "0/5 * * * * ?")
  public void partOrder() throws Exception {
    List<HashMap> orderList = orderService.selectTaskOrderList(); // 获取报名中的订单
    List<HashMap> beginPartOrders = orderService.selectBeginPartOrders(); // 获取报名中的订单
    Date now = new Date();
    /** 检查报名中订单 */
    for (HashMap order : orderList) { // 判断订单是否开始
      String startTime = (String) order.get("startTime");
      int workerNum = (int) order.get("workerNum");
      int recruitsNum = (int) order.get("recruitsNum");
      String jobId = (String) order.get("jobId");
      Date start = df.parse(startTime);
      if (workerNum == recruitsNum) {
        orderService.beginPartOrder(jobId); // 订单开始： 人数够了/时间到了
      }
      if (start.getTime() < now.getTime()) { // 订单开始
        if (workerNum == 0) {
          orderService.cancelPartOrder(jobId);
          System.out.println("订单" + jobId + "无劳工，订单取消");
        } else {
          List<HashMap> orderWorkers = orderService.selectPartOrderWorkers(jobId);
          for (HashMap orderWorker : orderWorkers) {
            System.out.println(orderWorker);
            String workerId = (String) orderWorker.get("userId");
            String status = (String) orderWorker.get("auditStatus");
            if (!status.equals("1")) orderService.updateWorkerApply(jobId, workerId, 5); // 取消
            else {
              orderService.updateWorkerSignIn(jobId, workerId);
              orderService.updateWorkerSignStatus(jobId, workerId, 1);
              System.out.println("劳工[" + workerId + "]完成签到！");
            }
          }
          orderService.beginPartOrder(jobId); // 更改订单状态 开始
          System.out.println("订单" + jobId + "开始，劳工数量为：" + workerNum);
        }
      }
    }

    /** 检查进行中订单 */
    for (HashMap order : beginPartOrders) { // 判断订单是否结束
      String endTime = (String) order.get("endTime");
      Date end = df.parse(endTime);
      String startTime = (String) order.get("startTime");
      Date start = df.parse(startTime);
      String jobId = (String) order.get("jobId");
      if (start.getTime() > now.getTime()) {
        int workerNum = (int) order.get("workerNum");
        int recruitsNum = (int) order.get("recruitsNum");
        if (workerNum < recruitsNum) orderMapper.setPartOrderStatus(jobId, 1); // 订单开始： 人数够了/时间到了
      }
      if (end.getTime() < now.getTime()) { // 订单完成
        BigDecimal amount = new BigDecimal("0.00");
        BigDecimal wages = (BigDecimal) order.get("wages"); // 普通工资
        BigDecimal leaderWages = (BigDecimal) order.get("leaderWages"); // 领队工资
        System.out.println("订单" + order.get("jobId") + "进行结束，工资详情：");
        List<HashMap> orderWorkers = orderService.selectPartOrderWorkers(jobId);
        for (HashMap orderWorker : orderWorkers) {
          String userId = (String) orderWorker.get("userId");
          Long signStatus = (Long) orderWorker.get("signStatus");
          int roleType = (int) orderWorker.get("roleType");
          orderService.updateWorkerSignOut(jobId, userId);
          orderService.updateWorkerSignStatus(jobId, userId, 2);
          System.out.println("劳工[" + userId + "]完成签退！");
          orderService.updateWorkerApply(userId, jobId, 3); // 更改用户报名订单状态 待收款
          orderService.updateWorkerFinishNum(userId); // 更改用户完成订单数量
          Wallet workerWallet = walletService.selectWallet(userId);
          BigDecimal depositbig = (BigDecimal) orderWorker.get("deposit");
          double deposit = depositbig.doubleValue();
          walletService.getDeposit(userId, deposit);
          walletService.userWalletDetail(
              userId,
              jobId,
              5,
              workerWallet.getTotalAmount() - deposit,
              workerWallet.getTotalAmount(),
              deposit,
              "退回押金");
          if (signStatus==2) {
            if (roleType == 1) { // 普通用户应得工资
              orderService.updateWorkerApplyWages(userId, jobId, wages);
              userService.addWorkerGrowth(userId, 60); // 普通员工成长值+60
              amount = amount.add(wages);
              System.out.println("用户" + userId + "完成订单,应得工资" + wages);
            } else if (roleType == 2) { // 领队应得工资
              orderService.updateWorkerApplyWages(userId, jobId, leaderWages);
              userService.addWorkerGrowth(userId, 80); // 领队成长值+80
              amount = amount.add(leaderWages);
              System.out.println("用户" + userId + "完成订单,应得工资" + leaderWages);
            }
          }
        }
        orderMapper.updatePartOrderAmount(jobId, amount);
        orderService.needPayPartOrder(jobId); // 更改订单状态 待支付
        System.out.println("订单" + jobId + "待结帐,总金额:" + amount);
      }
    }
  }
}
