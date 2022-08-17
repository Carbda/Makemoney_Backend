package com.backend.makemoney.mapper;

import com.backend.makemoney.entity.Resume;
import com.backend.makemoney.entity.User;
import com.backend.makemoney.entity.vo.EmployerList;
import com.backend.makemoney.entity.vo.UserList;
import com.backend.makemoney.entity.vo.WorkerList;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
  List<User> findAll();

  void setUserLogin(String userPhone);

  void setUserLogout(String userPhone);

  /** 上传用户头像 */
  void updateUserAvatar(@Param("userId") String userId, @Param("url") String url);

  /** 修改用户昵称 */
  int updateUserNickName(@Param("userId") String userId, @Param("nickName") String nickName);

  /** 增加用户 */
  void addUser(String userPhone, String userPwd, String userCreateTime);

  /** 修改密码 */
  void setUserPassword(String userPhone, String userPwd);

  /** 劳工提现 */
  /** 获取简历 */
  Resume selectResume(String userId);

  /** 更新用户简历 */
  void updateResume(Resume resume);

  /** 设置用户简历状态 */
  void updateResumeStatus(String userId);

  /** 收藏/取消收藏订单 */
  void updateCollection(
      @Param("userId") String userId,
      @Param("jobId") String jobId,
      @Param("type") String type,
      @Param("status") String status);

  /** 获取劳工兼职收藏列表 */
  List<HashMap> selectPartCollection(@Param("userId") String userId);

  /** 获取劳工全职收藏列表 */
  List<HashMap> selectFullCollection(@Param("userId") String userId);

//  /** 获取用户列表vo */
//  List<UserList> selectUserList(UserList userList);
//
//  /** 获取劳工列表vo */
//  List<WorkerList> selectWorkerList(WorkerList workerList);
//
//  /** 获取招聘方列表vo */
//  List<EmployerList> selectEmployerList(EmployerList employerList);

  /** 通过用户ID获取用户信息 */
  User selectUserByUserId(Long userId);

  /** 统计用户分布情况 */
  HashMap selectUserCount();

  /** 统计劳工分布情况 */
  HashMap selectWorkerCount();

  /** 统计招聘方分布情况 */
  HashMap selectEmployerCount();

  /** 用户信息卡-用户详情-用户资料 */
  HashMap selectUserInfoZiliao(Long userId);

  /** 用户信息卡-用户详情-资金统计 */
  HashMap selectUserInfoZijin(Long userId);

  /** 用户信息卡-用户详情-社交统计 */
  HashMap selectUserInfoShejiao(Long userId);

  /** 用户信息卡-用户详情-接单统计 */
  HashMap selectUserInfoJiedan(Long userId);

  /** 用户信息卡-用户详情-发单统计 */
  HashMap selectUserInfoFadan(Long userId);

  /** 用户信息卡-用户详情-邀请统计 */
  HashMap selectUserInfoYaoqing(Long userId);

  /** 用户信息卡-用户详情-公司资料 */
  HashMap selectUserInfoGongsi(Long userId);

  /** 用户信息卡-用户简历 */
  HashMap selectUserResume(String userId);

  /** 用户信息卡-资金明细 */
  //  HashMap selectUserFundDetails(Long userId);

  /** 用户信息卡-接单记录 */
  //  HashMap selectUserWorkRecord(Long userId);

  /** 用户信息卡-发单记录 */
  //  HashMap selectUserEmploy(Long userId);
  /** 用户信息卡-成长值记录 */

  /** 用户信息卡-审核信息 */

  /** 用户信息卡-邀请记录 */

  /** 用户信息卡-登陆日志 */

  /** 用户信息卡-聊天信息 */

  /** 通过手机号查找用户 */
  String selectUserIdByPhone(String userPhone);

  void addWorkerGrowth(@Param("userId") String userId,@Param("value") int value);

  void addEmployerGrowth(@Param("userId") String userId,@Param("value") int value);

  /** 获取用户列表vo */
  List<UserList> selectUserList(
      @Param("userId") String userId,
      @Param("userNickName") String userNickName,
      @Param("userCreateTime") String userCreateTime,
      @Param("pageNum") int pageNum,
      @Param("pageSize") int pageSize
  );
  /**获取用户列表长度**/
  int selectUserSum(
      @Param("userId") String userId,
      @Param("userNickName") String userNickName,
      @Param("userCreateTime") String userCreateTime
  );

  /** 获取劳工列表vo */
  List<WorkerList> selectWorkerList(
      @Param("userId") String userId,
      @Param("userNickName") String userNickName,
      @Param("userCreateTime") String userCreateTime,
      @Param("pageNum") int pageNum,
      @Param("pageSize") int pageSize
  );
  /**获取劳工列表总长读**/
  int selectWorkerSum(
      @Param("userId") String userId,
      @Param("userNickName") String userNickName,
      @Param("userCreateTime") String userCreateTime
  );

  /** 获取招聘方列表vo */
  List<EmployerList> selectEmployerList(
      @Param("userId") String userId,
      @Param("userNickName") String userNickName,
      @Param("userCreateTime") String userCreateTime,
      @Param("pageNum") int pageNum,
      @Param("pageSize") int pageSize
  );

  /**获取招聘方列表总长度**/
  int selectEmployerSum(
      @Param("userId") String userId,
      @Param("userNickName") String userNickName,
      @Param("userCreateTime") String userCreateTime
  );

  void deleteUser(String userId);

  void changeStatus(String userId, String userStatus);
  /**获取劳工接单情况**/
  int getTakeOrder(int min, int max);

  /**获取招聘方发单情况**/
  int getPublishOrder(int min, int max);

}
