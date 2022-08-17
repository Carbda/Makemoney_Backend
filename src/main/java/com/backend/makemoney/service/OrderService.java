package com.backend.makemoney.service;

import com.backend.makemoney.entity.PartOrder;
import com.backend.makemoney.entity.vo.OrderAuthList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
  List<PartOrder> getAllPartOrderbyPage(int pageNum, int pageSize);

  List<PartOrder> getAllPartOrder();
  /** 订单管理-通过审核的兼职订单 */
  List<HashMap> getOnPartOrder();

  /** 获取审核订单列表-兼职 */
  List<OrderAuthList> getPartOrderAuthList(OrderAuthList partOrderAuthList);

  /** 获取审核订单列表-全职 */
  List<OrderAuthList> getFullOrderAuthList(OrderAuthList fullOrderAuthList);

  /** APP */
  /** 获取兼职单详情+订单推荐 +报名信息 */
  Map getPartOrderInfo(String jobId, String label, Integer pageNum, String userId);

  /** 获取招聘首页数据（轮播图+发单列表） */
  Map getEmployerIndex(String userId);

  /** 获取招聘首页数据（轮播图+发单列表） */
  Map getWorkerIndex(Integer pageNum);

  /** 创建兼职订单 */
  void createPartOrder(PartOrder partOrder);

  /** 创建全职订单 */

  /** 下架兼职订单 */
  void closePartOrder(String jobId);

  /** 劳工工作台 */
  List<HashMap> getWorkerOrder(String userId);

  /** 招聘方工作台 */
  List<HashMap> getEmployerOrder(String userId);

  /** 审核兼职订单详情 */
  HashMap getAuthPartOrder(String jobId);

  /** 根据用户ID获取兼职发单列表 */

  /** 根据用户ID获取全职发单列表 */

  /**
   * 根据订单ID获取兼职订单信息
   *
   * @return
   */
  boolean selectOrderById(String jobId);

  /** 根据订单ID获取全职订单信息 */

  /** 审核订单 */

  /** 添加用户报名信息APPLY */
  void insertJobApply(String userId, String jobId, String type, String status, String auditMessage);

  /** 删除订单 */
  void cancelOrder(String userId, String jobId);

  /** 录取劳工 */
  void hireWorker(String userId, String jobId, String status);

  List<HashMap> selectOrderApply(String jobId);

  List<HashMap> selectTaskOrderList();

  void completePartOrder(String jobId);

  void beginPartOrder(String jobId);

  void needPayPartOrder(String jobId);

  void cancelPartOrder(String jobId);

  List<HashMap> selectPartOrderWorkers(String jobId);

  void updateWorkerFinishNum(String userId);

  void updateWorkerApply(String userId, String jobId,int status);

  List<HashMap> selectBeginPartOrders();

  void updateWorkerApplyWages(String userId, String jobId, BigDecimal wages);

  void addPartOrderApplyNum(String jobId);

  PartOrder selectPartOrder(String jobId);
  void addPartOrderWorkerNum(String jobId);
  void reducePartOrderWorkerNum(String jobId);
  HashMap selectTaskPartOrderByJobId(String jobId);
  HashMap selectUserApply(String jobId, String userId);
  void addPartOrderLeaderNum(String jobId);
  Map selectOrderApplyInfo(String jobId);
  List<HashMap> selectOrderPayInfo(String jobId);
  List<HashMap> selectOrderWorkerInfo(String jobId);
  HashMap selectOrderHeader(String jobId);
  void updateWorkerSignIn(String jobId,String userId);
  void updateWorkerSignOut(String jobId,String userId);
  void updateWorkerSignStatus(String jobId,String userId,int status);

  List<HashMap> selectJobs(String jobName);
  List<HashMap> selectMyJobs(String jobName,String userId);
}
