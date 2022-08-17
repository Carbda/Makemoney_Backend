package com.backend.makemoney.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface CountService {
  List<HashMap> workerRanking(String startTime,String endTime);
  List<HashMap> employerRanking(String startTime,String endTime);
  int[] countFinishOrder7days(String date);
  double[] countFinishOrderPay7days(String date);
  int[] selectWorkerNumBeforeDay(String date);
  int[] selectEmployerNumBeforeDay(String date);
  int[] countWithdrawalNumBefore7Days(String date);
  double[] countWithdrawalBefore7Days(String date);
  int[] countRechargeNumBefore7Days(String date);
  double[] countRechargeBefore7Days(String date);
  int[] countRegisterUserBeforeDay(String date);
  int[] countEmployerUserBeforeDay(String date);
}
