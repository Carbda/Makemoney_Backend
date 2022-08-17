package com.backend.makemoney.entity;

import lombok.Data;

@Data
public class Wallet {

  /** 钱包ID */
  private String userWalletId;

  /** 关联用户ID */
  private String associatedUserId;

  /** 冻结金额 */
  private Double totalFrozenAmount;

  /** 银行卡号码 */
  private String bankCard;

  /** 余额 */
  private Double totalAmount;

  /** 密码 */
  private String passWord;
}
