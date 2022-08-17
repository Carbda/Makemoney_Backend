package com.backend.makemoney.service.Impl;

import com.backend.makemoney.entity.Wallet;
import com.backend.makemoney.mapper.WalletMapper;
import com.backend.makemoney.service.WalletService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

  @Resource private WalletMapper walletMapper;
  /**
   * 获取用户钱包数据
   *
   * @param userId
   * @return
   */
  @Override
  public Wallet selectWallet(String userId) {
    return walletMapper.selectWallet(userId);
  }

  /**
   * 用户支付报名押金+记录金额流水
   *
   * @param userId
   * @param jobId
   * @param type
   * @param amountBefore
   * @param amountAfter
   * @param amount
   * @param remark
   */
  @Override
  public void payDeposit(
      String userId,
      String jobId,
      int type,
      Double amountBefore,
      Double amountAfter,
      Double amount,
      String remark) {
    walletMapper.userDeposit(userId);
    walletMapper.userWalletDetail(userId, jobId, type, amountBefore, amountAfter, amount, remark);
  }

  @Override
  public void walletPay(String userId, Double money) {
    walletMapper.walletPay(userId, money);
  }

  @Override
  public void userWalletDetail(
      String userId,
      String jobId,
      int type,
      Double amountBefore,
      Double amountAfter,
      Double amount,
      String remark) {
    walletMapper.userWalletDetail(userId, jobId, type, amountBefore, amountAfter, amount, remark);
  }

  /**
   * 设置支付密码
   *
   * @param userId
   * @param newPayPassword
   */
  @Override
  public void setPayPassword(String userId, String newPayPassword) {
    walletMapper.setPayPassword(userId, newPayPassword);
  }

  /**
   * 充值
   *
   * @param userId
   * @param money
   */
  @Override
  public void updateRecharge(String userId, Double money) {
    walletMapper.updateRecharge(userId, money);
  }

  /**
   * 提现申请
   *
   * @param userId
   * @param money
   * @param account
   */
  @Override
  public void insertWithdrawal(String userId, Double money, String account) {
    walletMapper.insertWithdrawal(userId, money, account);
  }

  @Override
  public List<HashMap> getRechargeList() {
    return walletMapper.getRechargeList();
  }

  @Override
  public List<HashMap> getWalletDetailList(String userId) {
    return walletMapper.getWalletDetailList(userId);
  }

  @Override
  public void addWallet(String userId, Double amount) {
    walletMapper.addWallet(userId,amount);
  }

  @Override
  public void getDeposit(String userId, Double amount) {
    walletMapper.getDeposit(userId,amount);
  }
}
