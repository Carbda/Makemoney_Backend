package com.backend.makemoney.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CountMapper {
  List<HashMap> workerRanking(@Param("startTime") String startTime,@Param("endTime")String endTime);
  List<HashMap> employerRanking(@Param("startTime") String startTime,@Param("endTime")String endTime);
  int selectWokerNum();
  int selectEmployerNum();
  int countFinishOrderBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  double countFinishOrderPayBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  int selectWorkerNumBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  int selectEmployerNumBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  double countWithdrawalBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  int countWithdrawalNumBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  double countRechargeBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  int countRechargeNumBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  int countRegisterUserBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
  int countEmployerUserBeforeDay(@Param("date") String date,@Param("beforeDay") int beforeDay);
}
