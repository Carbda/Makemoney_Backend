package com.backend.makemoney.controller.common;

import com.backend.makemoney.service.AdvertiseService;
import com.backend.makemoney.service.OrderService;
import com.backend.makemoney.utils.FileUploadUtils;
import com.backend.makemoney.utils.Result;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdvertiseController {
  @Resource private AdvertiseService advertiseService;
  @Resource private OrderService orderService;
  /**
   * 获取广告列表
   *
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/getAdvertise")
  public Result getAdvertise(@RequestBody Map map) {
    Integer pageNum = (Integer) map.get("pageNum");
    Integer pageSize = (Integer) map.get("pageSize");
    String adId = (String) map.get("adId");
    String adName = (String) map.get("adName");
    String associatedJob = (String) map.get("associatedJob");
    String adLocation = (String) map.get("adLocation");
    String adEndTime = (String) map.get("adEndTime");
    String adStatus = (String) map.get("adStatus");
    return Result.success(
        "广告列表",
        advertiseService.getAdvertise(
            pageNum, pageSize, adId, adName, associatedJob, adLocation, adEndTime, adStatus));
  }

  /**
   * 添加广告
   *
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/insertAdvertise")
  public Result insertAdvertise(
      @RequestParam("adName") String adName,
      @RequestParam("adLocation") String adLocation,
      @RequestParam("associatedJob") String associatedJob,
      @RequestParam("adStartTime") String adStartTime,
      @RequestParam("adEndTime") String adEndTime,
      @RequestParam("adStatus") String adStatus,
      @RequestParam("adRemark") String adRemark,
      @RequestParam("adImage") MultipartFile adImage) {
    if (orderService.selectOrderById(associatedJob) == false) return Result.error(500, "订单不存在");
    String baseDir = "/MakeMoney/upload/adPictures/" + associatedJob + "/";
    try {
      String url =
          "http://47.113.217.175:8080/pictures/adPictures/"
              + associatedJob
              + "/"
              + FileUploadUtils.upload(baseDir, adImage);
      advertiseService.insertAdvertise(
          adName, adLocation, associatedJob, url, adStartTime, adEndTime, adStatus, adRemark);
      return Result.success("添加广告成功");
    } catch (IOException e) {
      e.printStackTrace();
      return Result.error(500, "上传图片失败！");
    }
  }

  /**
   * 编辑广告
   *
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/updateAdvertise")
  public Result updateAdvertise(
      @RequestParam("adId") String adId,
      @RequestParam("adName") String adName,
      @RequestParam("adLocation") String adLocation,
      @RequestParam(value = "file") MultipartFile file,
      @RequestParam("adStartTime") String adStartTime,
      @RequestParam("adEndTime") String adEndTime,
      @RequestParam("adStatus") String adStatus,
      @RequestParam("adRemark") String adRemark,
      @RequestParam("associatedJob") String associatedJob) {
    if (orderService.selectOrderById(associatedJob) == false) return Result.error(500, "订单不存在");
    if(file.getSize()==0){
      advertiseService.updateAdvertiseNoImage(
          adId, adName, adLocation, adStartTime, adEndTime, adStatus, adRemark, associatedJob);
      return Result.success("编辑广告成功");
    }
    else {
      String baseDir = "/MakeMoney/upload/adPictures/" + associatedJob + "/";
      try {
        String url =
            "http://47.113.217.175:8080/pictures/adPictures/"
                + associatedJob
                + "/"
                + FileUploadUtils.upload(baseDir, file);
        advertiseService.updateAdvertise(
            adId, adName, adLocation, url, adStartTime, adEndTime, adStatus, adRemark, associatedJob);
        return Result.success("编辑广告成功");
      } catch (IOException e) {
        e.printStackTrace();
        return Result.error(500, "上传图片失败！");
      }
    }

  }
  @CrossOrigin
  @PostMapping(value = "/getAdvertiseSum")
  public Result getAdSum(@RequestBody Map map){
    Map<String, Object> result = new HashMap<>();
    String adId = (String) map.get("adId");
    String adName = (String) map.get("adName");
    String associatedJob = (String) map.get("associatedJob");
    String adLocation = (String) map.get("adLocation");
    String adEndTime = (String) map.get("adEndTime");
    String adStatus = (String) map.get("adStatus");
    result.put("sum",advertiseService.getAdSum(adId, adName, associatedJob, adLocation, adEndTime, adStatus));
    return Result.success("获取广告总数",result);
  }

  /**
   * 删除广告
   */
  @CrossOrigin
  @PostMapping(value = "/deleteAd")
  public Result deleteAd(@RequestParam("adId") String adId){
    advertiseService.deleteAd(adId);
    return Result.success("删除广告");
  }
}
