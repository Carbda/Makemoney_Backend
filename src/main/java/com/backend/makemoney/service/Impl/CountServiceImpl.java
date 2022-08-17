package com.backend.makemoney.service.Impl;

import com.backend.makemoney.mapper.CountMapper;
import com.backend.makemoney.service.CountService;
import com.backend.makemoney.utils.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CountServiceImpl implements CountService {
@Resource private CountMapper countMapper;

  /**
   * 劳工排行
   * @param startTime
   * @param endTime
   * @return
   */
  @Override
  public List<HashMap> workerRanking(String startTime, String endTime) {
    return countMapper.workerRanking(startTime,endTime);
  }

  /**
   * 招聘方排行
   * @param startTime
   * @param endTime
   * @return
   */
  @Override
  public List<HashMap> employerRanking(String startTime, String endTime) {
    return countMapper.employerRanking(startTime,endTime);
  }

  @Override
  public int[] countFinishOrder7days(String date) {
    int[] orderNum=new int[7];
    for(int i=0;i<7;i++){
      orderNum[i]=countMapper.countFinishOrderBeforeDay(date,6-i);
    }
    return orderNum;
  }

  @Override
  public double[] countFinishOrderPay7days(String date) {
    double[] orderPays=new double[7];
    for(int i=0;i<7;i++){
      orderPays[i]=countMapper.countFinishOrderPayBeforeDay(date,6-i);
    }
    return orderPays;
  }

  @Override
  public int[] selectWorkerNumBeforeDay(String date) {
    int[] workerNum=new int[7];
    for(int i=0;i<7;i++){
      workerNum[i]=countMapper.selectWorkerNumBeforeDay(date,6-i);
    }
    return workerNum;
  }

  @Override
  public int[] selectEmployerNumBeforeDay(String date) {
    int[] employerNum=new int[7];
    for(int i=0;i<7;i++){
      employerNum[i]=countMapper.selectEmployerNumBeforeDay(date,6-i);
    }
    return employerNum;
  }

  @Override
  public int[] countWithdrawalNumBefore7Days(String date) {
    int[] withdrawalNum=new int[7];
    for(int i=0;i<7;i++){
      withdrawalNum[i]=countMapper.countWithdrawalNumBeforeDay(date,6-i);
    }
    return withdrawalNum;
  }

  @Override
  public double[] countWithdrawalBefore7Days(String date) {
    double[] withdrawal=new double[7];
    for(int i=0;i<7;i++){
      withdrawal[i]=countMapper.countWithdrawalBeforeDay(date,6-i);
    }
    return withdrawal;
  }

  @Override
  public int[] countRechargeNumBefore7Days(String date) {
    int[] RechargeNum=new int[7];
    for(int i=0;i<7;i++){
      RechargeNum[i]=countMapper.countRechargeNumBeforeDay(date,6-i);
    }
    return RechargeNum;
  }

  @Override
  public double[] countRechargeBefore7Days(String date) {
    double[] Recharge=new double[7];
    for(int i=0;i<7;i++){
      Recharge[i]=countMapper.countRechargeBeforeDay(date,6-i);
    }
    return Recharge;
  }

  @Override
  public int[] countRegisterUserBeforeDay(String date) {
    int[] registerNum=new int[7];
    for(int i=0;i<7;i++){
      registerNum[i]=countMapper.countRegisterUserBeforeDay(date,6-i);
    }
    return registerNum;
  }

  @Override
  public int[] countEmployerUserBeforeDay(String date) {
    int[] employerNum=new int[7];
    for(int i=0;i<7;i++){
      employerNum[i]=countMapper.countEmployerUserBeforeDay(date,6-i);
    }
    return employerNum;
  }
}
