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
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
  //  static String verification = "";
  @Resource private UserService userService;
  //  @Resource private LogService logService;
  @Resource private RedisTemplate<String, Object> redisTemplate;

  /**
   * 注册
   */
  @RequestMapping(value = "/Register", method = RequestMethod.POST)
  public Result register(@RequestParam String userPhone, @RequestParam String userPwd,@RequestParam String userVerification) {
    List<User> users = userService.findAll();
    Object code = redisTemplate.opsForValue().get("Code:" + userPhone);
    if(code==null){ //验证码过期
      return  Result.error(HttpStatusEnum.USER_CODE_LOSS.getCode(),HttpStatusEnum.USER_CODE_LOSS.getMessage());
    }
    if (!code.equals(userVerification))
      return Result.error(  //验证码错误
          HttpStatusEnum.USER_CODE_FAIL.getCode(),
          HttpStatusEnum.USER_CODE_FAIL.getMessage());
    for (User i : users) {
      if (i.getUserPhone().equals(userPhone)) {
        return Result.error(
            HttpStatusEnum.USER_REGISTER_REPEAT.getCode(),
            HttpStatusEnum.USER_REGISTER_REPEAT.getMessage()); // 手机号已注册
      }
    }
    userService.addUser(
        String.valueOf(userPhone),
        String.valueOf(userPwd));
    return Result.success("新用户注册成功"); // 注册成功
  }

  @RequestMapping(value = "/GetCode", method = RequestMethod.POST)
  public boolean getcode(@RequestParam String phone) {

    String apiUrl = "https://sms_developer.zhenzikj.com";
    String appId = "110180";
    String appSecret = "63487043-9cf8-4664-97a6-157a398457e7";
    ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
    Map<String, Object> params = new HashMap<>();
    params.put("number", phone);
    params.put("templateId", "7031");
    String[] templateParams = new String[2];
    Random code = new Random();
    templateParams[0] = "";
    for (int i = 0; i < 6; i++) {
      templateParams[0] += code.nextInt(10);
    }
    redisTemplate
        .opsForValue()
        .set("Code:" + phone, templateParams[0], 5, TimeUnit.MINUTES);
    // verification = templateParams[0];
    templateParams[1] = "5";
    params.put("templateParams", templateParams);
    System.out.println(params);
    try {
      String result = client.send(params);
      System.out.println(result);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("发送失败");
      return false;
    }
  }
}
