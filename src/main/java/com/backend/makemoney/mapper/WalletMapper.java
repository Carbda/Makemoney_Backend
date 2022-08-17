package com.backend.makemoney.mapper;

import com.backend.makemoney.entity.Wallet;
import java.util.List;
import java.math.BigDecimal;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WalletMapper {
  /** 获取用户钱包数据 */
  Wallet selectWallet(String userId);
  /** 修改支付密码 */
  void setPayPassword(@Param("userId") String userId,@Param("newPayPassword") String newPayPassword);

  /** 钱包支出 */
  void walletPay(@Param("userId") String userId,@Param("money") Double money);

  /** 劳工充值 */
  void updateRecharge(@Param("userId") String userId,@Param("money") Double money);

  /** 劳工提现申请 */
  void insertWithdrawal(@Param("userId") String userId,@Param("money") Double money,@Param("account") String account);

  /** 用户报名交付押金 */
  void userDeposit(String userId);

  /** 用户钱包流水记录 */
  void userWalletDetail(
      @Param("userId") String userId,
      @Param("jobId") String jobId,
      @Param("type") int type,
      @Param("amountBefore") Double amountBefore,
      @Param("amountAfter") Double amountAfter,
      @Param("amount") Double amount,
      @Param("remark") String remark);

  /** 充值记录查看 */
  List<HashMap> getRechargeList();

  /** 用户账单列表 */
  List<HashMap> getWalletDetailList(@Param("userId") String userId);

  /** 获取对账中用户钱包统计，余额、累计提现、累计充值、冻结金额 */
  HashMap getWalletAmount(@Param("userId") String userId);

  /** 添加用户钱包 */
  void insertWallet(@Param("userId") String userId);

  void addWallet(@Param("userId") String userId,@Param("amount") Double amount);

  void getDeposit(@Param("userId") String userId,@Param("amount") Double amount);

}
