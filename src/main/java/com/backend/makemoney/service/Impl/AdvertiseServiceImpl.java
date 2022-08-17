package com.backend.makemoney.service.Impl;

import com.backend.makemoney.mapper.AdvertiseMapper;
import com.backend.makemoney.service.AdvertiseService;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {
  @Resource private AdvertiseMapper advertiseMapper;


  @Override
  public List<HashMap> getAdvertise(
      Integer pageNum,
      Integer pageSize,
      String adId,
      String adName,
      String associatedJob,
      String adLocation,
      String adEndTime,
      String adStatus) {
    pageNum = (pageNum - 1) * pageSize;
    return advertiseMapper.getAdvertise(pageNum, pageSize, adId, adName, associatedJob, adLocation, adEndTime, adStatus);
  }

  @Override
  public void insertAdvertise(
      String adName,
      String adLocation,
      String associatedJob,
      String adImage,
      String adStartTime,
      String adEndTime,
      String adStatus,
      String adRemark) {
    advertiseMapper.insertAdvertise(
        adName, adLocation, associatedJob, adImage, adStartTime, adEndTime, adStatus, adRemark);
  }

  @Override
  public void updateAdvertise(
      String adId,
      String adName,
      String adLocation,
      String adImage,
      String adStartTime,
      String adEndTime,
      String adStatus,
      String adRemark,
      String associatedJob) {
    advertiseMapper.updateAdvertise(
        adId,
        adName,
        adLocation,
        adImage,
        adStartTime,
        adEndTime,
        adStatus,
        adRemark,
        associatedJob);
  }

  @Override
  public void updateAdvertiseNoImage(String adId, String adName, String adLocation,
      String adStartTime, String adEndTime, String adStatus, String adRemark,
      String associatedJob) {
    advertiseMapper.updateAdvertiseNoImage(
        adId,
        adName,
        adLocation,
        adStartTime,
        adEndTime,
        adStatus,
        adRemark,
        associatedJob);
  }

  @Override
  public int getAdSum(
      String adId,
      String adName,
      String associatedJob,
      String adLocation,
      String adEndTime,
      String adStatus
  ){
    return advertiseMapper.getAdSum(adId, adName, associatedJob, adLocation, adEndTime, adStatus);
  }

  @Override
  public void deleteAd(String adId){
    advertiseMapper.deleteAd(adId);
  }
}
