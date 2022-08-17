package com.backend.makemoney.controller.login;


import com.backend.makemoney.entity.User;
import com.backend.makemoney.service.UserService;
import com.backend.makemoney.utils.HttpStatusEnum;
import com.backend.makemoney.utils.Result;
import com.zhenzi.sms.ZhenziSmsClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
//  static String verification = "";
  @Resource private UserService userService;
//  @Resource private LogService logService;
  @Resource private RedisTemplate<String, Object> redisTemplate;

  /**
   * 登录
   */
  @PostMapping(value = "/Login")
  public Result login(@RequestBody Map map) {
    List<User> users = userService.findAll();
    String userPhone = (String) map.get("userPhone");
    String userPwd = (String) map.get("userPwd");
    for (User user : users) {
      if (userPhone.equals(user.getUserPhone())) {
        if (userPwd.equals(user.getUserPwd())) { // 登录成功
          // 生成Token令牌
          String token = UUID.randomUUID() + "";
          // 存到Redis数据库
          redisTemplate.opsForValue().set(token, user);
          System.out.println("set");
          userService.setUserLogin(userPhone);
          System.out.println("log");
//          logService.addLog(userPhone,"用户","登录",'1');
          return Result.success(token, user);
        } else {    //密码错误
          return Result.error(
              HttpStatusEnum.USER_LOGIN_FAIL.getCode(),
              HttpStatusEnum.USER_LOGIN_FAIL.getMessage());
        }
      }
    }
    return Result.error(    //用户不存在
        HttpStatusEnum.USER_LOGIN_LOSS.getCode(), HttpStatusEnum.USER_LOGIN_LOSS.getMessage());
  }

  /**
   * 从redis中获取登陆用户信息
   *
   */
  @PostMapping(value = "/GetUserofLogin")
  public Result getUserofLogin(HttpServletRequest request) {
    String token = request.getHeader("token");
    Object user = redisTemplate.opsForValue().get(token);
    if (user != null) {
      return Result.success("获取用户信息成功", user);
    }
    return Result.error(
        HttpStatusEnum.USER_GETUSER_FAIL.getCode(), HttpStatusEnum.USER_GETUSER_FAIL.getMessage());
  }

  @PostMapping(value = "/LogOut")
  public Result logOut(HttpServletRequest request) {
    String token = request.getHeader("token");
    User user =(User) redisTemplate.opsForValue().get(token);
//    System.out.println(user);
    if(user ==null){
      return Result.success("用户当前为离线状态");
    }
//    System.out.println(user.getUserPhone());
    String userPhone=user.getUserPhone();
    redisTemplate.expire(token, 1, TimeUnit.SECONDS);
    userService.setUserLogout(userPhone);
//    logService.addLog(userPhone,"用户","退出登录",'1');
    return Result.success("用户退出登录");
  }

  @PostMapping(value = "/ResetPassword")
  public Result FindPassword(@RequestParam String userPhone,@RequestParam String userPwd,@RequestParam String userVerification){
    Object code = redisTemplate.opsForValue().get("Code:"+userPhone);
    if(code == null){ //验证码过期
      return Result.error(HttpStatusEnum.USER_CODE_LOSS.getCode(), HttpStatusEnum.USER_CODE_LOSS.getMessage());
    }
    if(!code.equals(userVerification)){ //验证码错误
      return Result.error(HttpStatusEnum.USER_CODE_FAIL.getCode(), HttpStatusEnum.USER_CODE_FAIL.getMessage());
    }
    userService.setUserPassword(userPhone,userPwd);
//    logService.addLog(userPhone,"用户","通过验证码重置密码",'1');
    return Result.success("重置密码成功");

  }
}
