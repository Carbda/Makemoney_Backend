package com.backend.makemoney.service;

import com.backend.makemoney.entity.Wallet;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {

  /** 获取用户钱包 */
  Wallet selectWallet(String userId);

  /** 用户支付报名押金 */
  void payDeposit(
      String userId,
      String jobId,
      int type,
      Double amountBefore,
      Double amountAfter,
      Double amount,
      String remark);

  void walletPay(String userId,Double money);

  void userWalletDetail(
      String userId,
      String jobId,
      int type,
      Double amountBefore,
      Double amountAfter,
      Double amount,
      String remark);

  void setPayPassword(String userId, String newPayPassword);

  void updateRecharge(String userId, Double money);

  void insertWithdrawal(String userId, Double money, String account);

  List<HashMap> getRechargeList();

  List<HashMap> getWalletDetailList(String userId);
  void addWallet(String userId,Double amount);
  void getDeposit(String userId,Double amount);
}
