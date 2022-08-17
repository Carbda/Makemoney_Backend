package com.backend.makemoney.service;

import com.backend.makemoney.entity.Resume;
import com.backend.makemoney.entity.User;
import com.backend.makemoney.entity.vo.EmployerList;
import com.backend.makemoney.entity.vo.UserList;
import com.backend.makemoney.entity.vo.WorkerList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  List<User> findAll();

  void addUser(String userPhone, String userPwd);
  void uploadUserAvatar(String userId,String url);
  void setUserLogin(String userPhone);
  void setUserLogout(String userPhone);
  void setUserPassword(String userPhone,String userPwd);
  int updateUserNickName(String userId,String nickName);
  User selectUserByUserId(Long userId);
  Resume selectResume(String userId);
  void updateResume(Resume resume);
  void updateResumeStatus(String userId);
  HashMap selectUserCount();
  HashMap selectWorkerCount();
  HashMap selectEmployerCount();
//  List<UserList> selectUserList(UserList userList);
//  List<WorkerList> selectWorkerList(WorkerList workerList);
//  List<EmployerList> selectEmployerList(EmployerList employerList);
List<UserList> selectUserList(String userId,String userNickName, String userCreateTime,int pageNum, int pageSize);
  List<WorkerList> selectWorkerList(String userId,String userNickName, String userCreateTime,int pageNum, int pageSize);
  List<EmployerList> selectEmployerList(String userId,String userNickName, String userCreateTime,int pageNum, int pageSize);

  int selectWorkerSum(String userId,String userNickName, String userCreateTime);
  int selectUserSum(String userId,String userNickName, String userCreateTime);
  int selectEmployerSum(String userId,String userNickName, String userCreateTime);
  void deleteUser(String userId);
  void changeStatus(String userId, String userStatus);
  void updateCollection(String userId,String jobId,String type,String status);
  Map selectUserInfo(Long userId);
  List<HashMap> selectPartCollection(String userId);
  List<HashMap> selectFullCollection(String userId);
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
  void addWorkerGrowth(String userId,int value);

  void addEmployerGrowth(String userId,int value);
  /**获取劳工接单情况**/
  List<Integer> getTakeOrder();
  /**获取招聘方发单情况**/
  List<Integer> getPublishOrder();

}
