package com.backend.makemoney.service;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface AdvertiseService {
  List<HashMap> getAdvertise(
      Integer pageNum,
      Integer pageSize,
      String adId,
      String adName,
      String associatedJob,
      String adLocation,
      String adEndTime,
      String adStatus);

  void insertAdvertise(
      String adName,
      String adLocation,
      String associatedJob,
      String adImage,
      String adStartTime,
      String adEndTime,
      String adStatus,
      String adRemark);

  void updateAdvertise(
      String adId,
      String adName,
      String adLocation,
      String adImage,
      String adStartTime,
      String adEndTime,
      String adStatus,
      String adRemark,
      String associatedJob);
  void updateAdvertiseNoImage(
      String adId,
      String adName,
      String adLocation,
      String adStartTime,
      String adEndTime,
      String adStatus,
      String adRemark,
      String associatedJob);
  int getAdSum(
      String adId,
      String adName,
      String associatedJob,
      String adLocation,
      String adEndTime,
      String adStatus
  );

  void deleteAd(String adId);
}
