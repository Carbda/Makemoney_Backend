package com.backend.makemoney.controller.user;

import com.backend.makemoney.entity.PartOrder;
import com.backend.makemoney.entity.Wallet;
import com.backend.makemoney.mapper.OrderMapper;
import com.backend.makemoney.service.OrderService;
import com.backend.makemoney.service.UserService;
import com.backend.makemoney.service.WalletService;
import com.backend.makemoney.utils.Result;
import java.util.List;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wallet")
public class WalletController {
  @Resource private WalletService walletService;
  @Resource private OrderService orderService;
  @Resource private UserService userService;
  /**
   * 获取用户钱包数据
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/{userId}")
  public Result getWallet(@PathVariable("userId") String userId) {
    return Result.success("获取用户钱包数据", walletService.selectWallet(userId));
  }
  /**
   * 修改支付密码
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/setPayPassword")
  public Result setPayPassword(@RequestBody Map map) {
    String userId = (String) map.get("userId");
    String payPassword = (String) map.get("newPayPassword");
    walletService.setPayPassword(userId, payPassword);
    return Result.success("修改支付密码成功");
  }

  /**
   * 充值
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/recharge")
  public Result recharge(@RequestBody Map map) {
    String userId = (String) map.get("userId");
    String str = (String) map.get("money");
    Double money = Double.parseDouble(str);
    Wallet wallet = walletService.selectWallet(userId);
    walletService.updateRecharge(userId, money);
    walletService.userWalletDetail(
        userId,
        "NULL",
        1,
        wallet.getTotalAmount(),
        wallet.getTotalAmount() + money,
        money,
        "充值金额" + money + "元");
    return Result.success("充值金额" + money + "元成功！");
  }

  /**
   * 提现申请
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/withdrawal")
  public Result withdrawal(@RequestBody Map map) {
    String userId = (String) map.get("userId");
    Wallet wallet = walletService.selectWallet(userId);
    String str = (String) map.get("money");
    Double money = Double.parseDouble(str);
    String account = (String) map.get("account");
    if (wallet.getTotalAmount() < money) {
      return Result.error(500, "余额不足");
    }
    walletService.walletPay(userId, money);
    walletService.insertWithdrawal(userId, money, account);
    return Result.success("提现申请已提交");
  }

  /**
   * 获取用户充值记录列表
   *
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/getRechargeList")
  public Result setPayPassword() {
    return Result.success("获取充值记录列表", walletService.getRechargeList());
  }

  @CrossOrigin
  @GetMapping(value = "/getWalletDetailList")
  public Result getWalletDetailList(@RequestParam String userId) {
    return Result.success("获取用户[" + userId + "]账单列表", walletService.getWalletDetailList(userId));
  }

  /** 订单结账 ： 招聘方支付 + 用户收款 */
  @CrossOrigin
  @PostMapping(value = "/payPartOrder")
  public Result payPartOrder(@RequestBody Map map) {
    String jobId = (String) map.get("jobId");
    String userId = (String) map.get("userId");
    String amountstr = (String) map.get("amount");
    double amount = Double.parseDouble(amountstr);
    String password = (String) map.get("password");
    Wallet userWallet = walletService.selectWallet(userId);
    if(!userWallet.getPassWord().equals(password)) return Result.error(205,"支付密码错误");
    // 1.判断用户钱包余额，余额不足返回
    if (userWallet.getTotalAmount() < amount) return Result.error(500, "用户钱包余额不足，请充值后结账！");
    // 2.扣款，将扣款记录保存在wallet detail中
    walletService.walletPay(userId, amount);
    walletService.userWalletDetail(
        userId,
        jobId,
        3,
        userWallet.getTotalAmount() + amount,
        userWallet.getTotalAmount(),
        amount,
        "订单结账");
    // 3.更改订单状态,账单完成
    orderService.completePartOrder(jobId);
    // 4.用户成长值提高，成长值记录在历史表中
    userService.addEmployerGrowth(userId, 50); // 招聘方成长值+50
    // 5.查找该订单的报名表
    List<HashMap> orderWorkers = orderService.selectPartOrderWorkers(jobId);
    for (HashMap orderWorker : orderWorkers) {
      String workerId = (String) orderWorker.get("userId");
      int workerType = (int) orderWorker.get("roleType");
      BigDecimal bigDecimal = (BigDecimal) orderWorker.get("salary");
      double salary = bigDecimal.doubleValue();
      Wallet workerWallet = walletService.selectWallet(userId);
      walletService.addWallet(userId, salary);
      walletService.userWalletDetail(
          workerId,
          jobId,
          4,
          workerWallet.getTotalAmount() - salary,
          workerWallet.getTotalAmount(),
          salary,
          "订单收入");
      // 6.根据角色类型向用户钱包中打钱，退押金， 保存收款记录，更改报名表状态，用户成长值提高，成长值记录在历史表中
      orderService.updateWorkerApply(jobId, workerId, 4);
    }
    return Result.success("用户结账成功");
  }
}
