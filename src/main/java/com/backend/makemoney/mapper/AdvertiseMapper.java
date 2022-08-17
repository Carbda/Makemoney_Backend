package com.backend.makemoney.mapper;

import java.util.List;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdvertiseMapper {

  /** 获取广告列表 */
  List<HashMap> getAdvertise(
      @Param("pageNum") Integer pageNum,
      @Param("pageSize") Integer pageSize,
      @Param("adId") String adId,
      @Param("adName") String adName,
      @Param("associatedJob") String associatedJob,
      @Param("adLocation") String adLocation,
      @Param("adEndTime") String adEndTime,
      @Param("adStatus") String adStatus);

  /** 添加广告 */
  void insertAdvertise(
      @Param("adName") String adName,
      @Param("adLocation") String adLocation,
      @Param("associatedJob") String associatedJob,
      @Param("adImage") String adImage,
      @Param("adStartTime") String adStartTime,
      @Param("adEndTime") String adEndTime,
      @Param("adStatus") String adStatus,
      @Param("adRemark") String adRemark);

  void updateAdvertise(
      @Param("adId") String adId,
      @Param("adName") String adName,
      @Param("adLocation") String adLocation,
      @Param("adImage") String adImage,
      @Param("adStartTime") String adStartTime,
      @Param("adEndTime") String adEndTime,
      @Param("adStatus") String adStatus,
      @Param("adRemark") String adRemark,
      @Param("associatedJob") String associatedJob);
  void updateAdvertiseNoImage(
      @Param("adId") String adId,
      @Param("adName") String adName,
      @Param("adLocation") String adLocation,
      @Param("adStartTime") String adStartTime,
      @Param("adEndTime") String adEndTime,
      @Param("adStatus") String adStatus,
      @Param("adRemark") String adRemark,
      @Param("associatedJob") String associatedJob);

  int getAdSum(
      @Param("adId") String adId,
      @Param("adName") String adName,
      @Param("associatedJob") String associatedJob,
      @Param("adLocation") String adLocation,
      @Param("adEndTime") String adEndTime,
      @Param("adStatus") String adStatus);

  void deleteAd(@Param("adId") String adId);
}
