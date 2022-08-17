package com.backend.makemoney.controller.user;

import com.backend.makemoney.entity.Resume;
import com.backend.makemoney.entity.User;
import com.backend.makemoney.entity.Wallet;
import com.backend.makemoney.entity.vo.EmployerList;
import com.backend.makemoney.entity.vo.UserList;
import com.backend.makemoney.entity.vo.WorkerList;
import com.backend.makemoney.service.UserService;
import com.backend.makemoney.service.WalletService;
import com.backend.makemoney.utils.FileUploadUtils;
import com.backend.makemoney.utils.HttpStatusEnum;
import com.backend.makemoney.utils.Result;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
  @Resource private UserService userService;
  @Resource private WalletService walletService;

  @CrossOrigin
  @PostMapping(value = "/uploadAvatar")
  public Result uploadAvatar(
      @RequestParam("userId") String userId, @RequestParam("file") MultipartFile file) {
    String baseDir = "/MakeMoney/upload/" + userId + "/";
    try {
      String url =
          "http://47.113.217.175:8080/pictures/"
              + userId
              + "/"
              + FileUploadUtils.upload(baseDir, file);
      userService.uploadUserAvatar(userId, url);
      return Result.success("上传头像成功", url);
    } catch (IOException e) {
      e.printStackTrace();
      return Result.error(500, "上传图片失败！");
    }
  }

  /**
   * 更改用户昵称
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/updateNickName")
  public Result updateUserNickName(@RequestBody Map map) {
    if (userService.updateUserNickName((String) map.get("userId"), (String) map.get("nickName"))
        == 1) {
      return Result.success("更新用户昵称");
    } else return Result.error(201, "用户不存在");
  }


  @CrossOrigin
  @GetMapping(value = "/collectOrder")
  public Result setCollectOrder(
      @RequestParam("userId") String userId,
      @RequestParam("jobId") String jobId,
      @RequestParam("type") String type,
      @RequestParam("status") String status) {
    userService.updateCollection(userId, jobId, type, status);
    if (status.equals("0")) return Result.success("取消收藏订单" + jobId);
    return Result.success("收藏订单" + jobId);
  }

  /**
   * 用户类信息
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/{userId}")
  public Result getUserByUserId(@PathVariable("userId") Long userId) {
    User user = userService.selectUserByUserId(userId);
    if (user == null) {
      return Result.error(
          HttpStatusEnum.USER_FIND_LOSS.getCode(), HttpStatusEnum.USER_FIND_LOSS.getMessage());
    }
    return Result.success("查找用户信息成功", user);
  }

  /**
   * 用户信息卡-用户详情
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/userInfo/{userId}")
  public Result getUserCardInfo(@PathVariable("userId") Long userId) {
    Map user = userService.selectUserInfo(userId);
    if (user == null) {
      return Result.error(
          HttpStatusEnum.USER_FIND_LOSS.getCode(), HttpStatusEnum.USER_FIND_LOSS.getMessage());
    }
    return Result.success("用户信息卡-用户详情", userService.selectUserInfo(userId));
  }

  /**
   * 获取用户简历信息
   *
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/resume/{userId}")
  public Result getResume(@PathVariable("userId") String userId) {
    return Result.success("用户简历", userService.selectResume(userId));
  }

  /**
   * 更新用户简历
   *
   * @param resume
   * @return
   */
  @CrossOrigin
  @PostMapping(value = "/updateResume")
  public Result updateResume(@RequestBody Resume resume) {
    userService.updateResume(resume);
    userService.updateResumeStatus(resume.getUserId());
    return Result.success("更新用户简历", userService.selectResume(resume.getUserId()));
  }

  /**
   * 获取兼职收藏列表
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/partCollection")
  public Result getPartCollection(@RequestParam("userId") String userId) {
    return Result.success("用户兼职订单收藏列表",userService.selectPartCollection(userId));
  }

  /**
   * 获取全职收藏列表
   * @param userId
   * @return
   */
  @CrossOrigin
  @GetMapping(value = "/fullCollection")
  public Result getFullCollection(@RequestParam("userId") String userId) {
    return Result.success("用户全职订单收藏列表",userService.selectFullCollection(userId));
  }
  @CrossOrigin
  @PostMapping(value = "/getUserList")
  public Result getUserList(@RequestBody Map map) {
    int pageNum = (int) map.get("pageNum");
    int pageSize = (int) map.get("pageSize");
    String userId = (String) map.get("userId");
    String userNickName = (String) map.get("userNickName");
    String userCreateTime = (String) map.get("userCreateTime");
    Map<String, Object> result = new HashMap<>();
    result.put("userList", userService.selectUserList(userId,userNickName, userCreateTime, pageNum, pageSize));
    result.put("userComposition", userService.selectUserCount());
    return Result.success("获取用户列表", result);
  }
  @CrossOrigin
  @PostMapping(value = "/getUserSum")
  public Result getUserListSum(@RequestBody Map map){
    String userId = (String) map.get("userId");
    String userNickName = (String) map.get("userNickName");
    String userCreateTime = (String) map.get("userCreateTime");
    Map<String, Object> result = new HashMap<>();
    int sum = userService.selectUserSum(userId,userNickName, userCreateTime);
    result.put("sum",sum);
    return Result.success("获取用户列表总长度", result);
  }

  @CrossOrigin
  @PostMapping(value = "/getWorkerSum")
  public Result getWorkerListSum(@RequestBody Map map){
    String userId = (String) map.get("userId");
    String userNickName = (String) map.get("userNickName");
    String userCreateTime = (String) map.get("userCreateTime");
    Map<String, Object> result = new HashMap<>();
    int sum = userService.selectWorkerSum(userId,userNickName, userCreateTime);
    result.put("sum", sum);
    return Result.success("获取用户列表总长度", result);
  }

  @CrossOrigin
  @PostMapping(value = "/getEmployerSum")
  public Result getEmployerListSum(@RequestBody Map map){
    String userId = (String) map.get("userId");
    String userNickName = (String) map.get("userNickName");
    String userCreateTime = (String) map.get("userCreateTime");
    Map<String, Object> result = new HashMap<>();
    int sum = userService.selectEmployerSum(userId,userNickName, userCreateTime);
    result.put("sum", sum);
    return Result.success("获取用户列表总长度", result);
  }
  /**
   * 获取劳工列表，根据workerList参数查询
   */
  @CrossOrigin
  @PostMapping(value = "/getWorkerList")
  public Result getWorkerList(@RequestBody Map map) {
    int pageNum = (int) map.get("pageNum");
    int pageSize = (int) map.get("pageSize");
    String userId = (String) map.get("userId");
    String userNickName = (String) map.get("userNickName");
    String userCreateTime = (String) map.get("userCreateTime");
    Map<String, Object> result = new HashMap<>();
    result.put("workerList", userService.selectWorkerList(userId,userNickName, userCreateTime, pageNum, pageSize));
    result.put("workerComposition", userService.selectWorkerCount());
    result.put("takeOrderData", userService.getTakeOrder());
    return Result.success("获取劳工列表", result);
  }

  /**
   * 获取招聘方列表，根据employerList参数查询
   */
  @CrossOrigin
  @PostMapping(value = "/getEmployerList")
  public Result getEmployerList(@RequestBody Map map) {
    int pageNum = (int) map.get("pageNum");
    int pageSize = (int) map.get("pageSize");
    String userId = (String) map.get("userId");
    String userNickName = (String) map.get("userNickName");
    String userCreateTime = (String) map.get("userCreateTime");
    Map<String, Object> result = new HashMap<>();
    result.put("employerList", userService.selectEmployerList(userId,userNickName, userCreateTime, pageNum, pageSize));
    result.put("employerComposition", userService.selectEmployerCount());
    result.put("publishOrderData", userService.getPublishOrder());
    return Result.success("获取招聘方列表", result);
  }
  /**
   * 删除用户
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping("/deleteUser")
  public Result deleteUser(@RequestParam Map map){
    String userId = (String) map.get("userId");
    userService.deleteUser(userId);
    return Result.success("已删除用户【"+ userId +"】");
  }
  /**
   * 修改用户状态
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping("changeStatus")
  public Result changeStatus(@RequestParam Map map){
    String userId = (String) map.get("userId");
    String userStatus = (String) map.get("userStatus");
    userService.changeStatus(userId, userStatus);
    return Result.success("已修改用户"+ userId +"状态为" + userStatus);
  }
  @CrossOrigin
  @GetMapping(value = "/getUserResume")
  public Result getUserResume(@RequestParam("userId") String userId) {
    return Result.success("获取用户【"+userId+"】简历",userService.selectUserResume(userId));
  }
}
