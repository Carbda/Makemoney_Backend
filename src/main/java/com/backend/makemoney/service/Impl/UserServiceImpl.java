package com.backend.makemoney.service.Impl;

import com.backend.makemoney.entity.Resume;
import com.backend.makemoney.entity.User;
import com.backend.makemoney.entity.vo.EmployerList;
import com.backend.makemoney.entity.vo.UserList;
import com.backend.makemoney.entity.vo.WorkerList;
import com.backend.makemoney.mapper.UserMapper;
import com.backend.makemoney.mapper.WalletMapper;
import com.backend.makemoney.service.UserService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Resource private UserMapper userMapper;
@Resource private WalletMapper walletMapper;
  @Override
  public List<User> findAll() {
    return userMapper.findAll();
  }

  @Override
  public void addUser(String userPhone, String userPwd) {
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("yyyy-MM-dd HH-mm-ss");
    Date date = new Date();
    String userCreateTime = sdf.format(date);
    userMapper.addUser(userPhone, userPwd, userCreateTime);
    String userId=userMapper.selectUserIdByPhone(userPhone);
    walletMapper.insertWallet(userId);
  }
  /**
   * 获取用户列表
   * @param userId
   * @param userNickName
   * @param userCreateTime
   * @param pageNum
   * @param pageSize
   * @return
   */
  @Override
  public List<UserList> selectUserList(String userId,String userNickName, String userCreateTime,int pageNum, int pageSize) {
    return userMapper.selectUserList(userId,userNickName, userCreateTime, (pageNum-1)*pageSize, pageSize);
  }

  @Override
  public int selectUserSum(String userId,String userNickName, String userCreateTime){
    return userMapper.selectUserSum(userId,userNickName, userCreateTime);
  }

  @Override
  public int selectWorkerSum(String userId,String userNickName, String userCreateTime){
    return userMapper.selectUserSum(userId,userNickName, userCreateTime);
  }

  @Override
  public int selectEmployerSum(String userId,String userNickName, String userCreateTime){
    return userMapper.selectUserSum(userId,userNickName, userCreateTime);
  }
  /**
   * 获取劳工方列表
   * @param userId
   * @param userNickName
   * @param userCreateTime
   * @param pageNum
   * @param pageSize
   * @return
   */
  @Override
  public List<WorkerList> selectWorkerList(String userId,String userNickName, String userCreateTime,int pageNum, int pageSize) {
    return userMapper.selectWorkerList(userId,userNickName, userCreateTime, (pageNum-1)*pageSize, pageSize);
  }

  /**
   * 获取招聘方列表
   * @param userId
   * @param userNickName
   * @param userCreateTime
   * @param pageNum
   * @param pageSize
   * @return
   */
  @Override
  public List<EmployerList> selectEmployerList(String userId,String userNickName, String userCreateTime,int pageNum, int pageSize) {
    return userMapper.selectEmployerList(userId,userNickName, userCreateTime, (pageNum-1)*pageSize , pageSize);
  }
  /**
   * 上传用户头像
   *
   */
  @Override
  public void uploadUserAvatar(String userId, String url) {
    userMapper.updateUserAvatar(userId, url);
  }

  @Override
  public void setUserLogin(String userPhone) {
    userMapper.setUserLogin(userPhone);
  }

  @Override
  public void setUserLogout(String userPhone) {
    userMapper.setUserLogout(userPhone);
  }

  @Override
  public void setUserPassword(String userPhone, String userPwd) {
    userMapper.setUserPassword(userPhone, userPwd);
  }
  @Override
  public void deleteUser(String userId){
    userMapper.deleteUser(userId);
  }

  @Override
  public void changeStatus(String userId, String userStatus){
    userMapper.changeStatus(userId, userStatus);
  }
  /**
   * 修改用户昵称
   *
   * @param userId
   * @param nickName
   */
  @Override
  public int updateUserNickName(String userId, String nickName) {
    return userMapper.updateUserNickName(userId, nickName);
  }

  /**
   * 通过用户ID查找用户信息
   *
   * @param userId APP用户主键
   * @return
   */
  @Override
  public User selectUserByUserId(Long userId) {
    return userMapper.selectUserByUserId(userId);
  }

  /**
   * 获取用户简历
   *
   * @param userId
   * @return
   */
  @Override
  public Resume selectResume(String userId) {
    return userMapper.selectResume(userId);
  }

  /**
   * 更新用户简历
   *
   * @param resume
   */
  @Override
  public void updateResume(Resume resume) {
    userMapper.updateResume(resume);
  }

  /**
   * 更新用户简历状态
   *
   * @param userId
   */
  @Override
  public void updateResumeStatus(String userId) {
    userMapper.updateResumeStatus(userId);
  }

  /**
   * 统计用户分布情况
   *
   * @return
   */
  @Override
  public HashMap selectUserCount() {
    return userMapper.selectUserCount();
  }

  /**
   * 统计劳工等级分布
   *
   * @return
   */
  @Override
  public HashMap selectWorkerCount() {
    return userMapper.selectWorkerCount();
  }

  /**
   * 统计招聘方等级分布
   *
   * @return
   */
  @Override
  public HashMap selectEmployerCount() {
    return userMapper.selectEmployerCount();
  }


  /**
   * 取消/收藏订单
   *
   * @param userId
   * @param jobId
   * @param type
   * @param status
   */
  @Override
  public void updateCollection(String userId, String jobId, String type, String status) {
    userMapper.updateCollection(userId, jobId, type, status);
  }

  /**
   * 用户信息卡-用户详情
   *
   * @param userId
   * @return
   */
  @Override
  public Map selectUserInfo(Long userId) {
    Map userInfo = new HashMap();
    userInfo.put("userData", userMapper.selectUserInfoZiliao(userId));
    userInfo.put("fundStatistics", userMapper.selectUserInfoZijin(userId));
    userInfo.put("socialContact", userMapper.selectUserInfoShejiao(userId));
    userInfo.put("employee", userMapper.selectUserInfoJiedan(userId));
    userInfo.put("employer", userMapper.selectUserInfoFadan(userId));
    userInfo.put("invitation", null);
    userInfo.put("enterprise", userMapper.selectUserInfoGongsi(userId));
    return userInfo;
  }

  /**
   * 获取兼职订单
   * @param userId
   * @return
   */
  @Override
  public List<HashMap> selectPartCollection(String userId) {
    return userMapper.selectPartCollection(userId);
  }

  /**
   * 获取全职订单
   * @param userId
   * @return
   */
  @Override
  public List<HashMap> selectFullCollection(String userId) {
    return userMapper.selectFullCollection(userId);
  }

  /**
   * 获取用户简历
   * @param userId
   * @return
   */
  @Override
  public HashMap selectUserResume(String userId) {
    return userMapper.selectUserResume(userId);
  }

  @Override
  public void addWorkerGrowth(String userId, int value) {
    userMapper.addWorkerGrowth(userId,value);
  }

  @Override
  public void addEmployerGrowth(String userId, int value) {
    userMapper.addEmployerGrowth(userId,value);
  }

  /**获取劳工接单情况**/
  @Override
  public List<Integer> getTakeOrder(){
    List<Integer> list = new ArrayList<>();
    list.add(userMapper.getTakeOrder(0, 5));
    list.add(userMapper.getTakeOrder(5, 10));
    list.add(userMapper.getTakeOrder(10, 20));
    list.add(userMapper.getTakeOrder(20, 30));
    list.add(userMapper.getTakeOrder(30, 50));
    list.add(userMapper.getTakeOrder(50, 9999));
    return list;
  };
  /**获取招聘方发单情况**/
  @Override
  public List<Integer> getPublishOrder(){
    List<Integer> list = new ArrayList<>();
    list.add(userMapper.getPublishOrder(0, 5));
    list.add(userMapper.getPublishOrder(5, 10));
    list.add(userMapper.getPublishOrder(10, 20));
    list.add(userMapper.getPublishOrder(20, 30));
    list.add(userMapper.getPublishOrder(30, 50));
    list.add(userMapper.getPublishOrder(50, 9999));
    return list;
  };

}
