package com.backend.makemoney.mapper;

import com.backend.makemoney.entity.PartOrder;
import com.backend.makemoney.entity.vo.OrderAuthList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
  List<PartOrder> findAll();

  /** 订单管理-通过审核的兼职订单 */
  List<HashMap> selectOnPartOrder();

  /** 获取用户报名信息 */
  HashMap selectUserApply(String jobId, String userId);
  /** 获取审核订单列表-兼职 */
  List<OrderAuthList> selectPartOrderAuthList(OrderAuthList partOrderAuthList);

  /** 获取审核订单列表-全职 */
  List<OrderAuthList> selectFullOrderAuthList(OrderAuthList fullOrderAuthList);

  /** 获取审核订单详情 */

  /** 获取招聘审核列表 */

  /** APP下架订单 */
  void closePartOrder(String jobId);

  /** APP */
  /** 兼职订单详情 */
  PartOrder selectPartOrder(String jobId);

  /** 推荐订单列表 */
  List<HashMap> selectRecommendPartOrder(String label, Integer pageNum);

  /** 获取首页轮播图 */
  List<HashMap> selectAdvertise();

  /** 获取招聘首页发单列表 */
  List<HashMap> selectEmployerIndex(String userId);

  /** 获取招聘首页发单列表 */
  List<HashMap> selectWorkerIndex(Integer pageNum);

  /** 创建兼职订单 */
  void insertPartOrder(PartOrder partOrder);

  /** 劳工工作台 */
  List<HashMap> selectWorkerOrder(String userId);

  /** 招聘方工作台 */
  List<HashMap> selectEmployerOrder(String userId);

  /** 创建全职订单 */

  /** 获取审核订单信息 */
  HashMap selectAuthPartOrder(String jobId);

  /** 根据用户ID获取兼职发单列表 */

  /** 根据用户ID获取全职发单列表 */

  /** 根据订单ID获取兼职订单信息 */

  /** 根据订单ID获取全职订单信息 */

  /** 添加用户报名信息APPLY */
  void insertJobApply(
      @Param("userId") String userId,
      @Param("jobId") String jobId,
      @Param("type") String type,
      @Param("status") String status,
      @Param("auditMessage") String auditMessage);
  /** 关闭订单 */

  /** 通过ID找订单 */
  HashMap selectOrderById(@Param("jobId") String jobId);

  /** 取消报名 */
  void cancelOrder(@Param("userId") String userId,@Param("jobId") String jobId);

  /** 录取劳工 */
  void hireWorker(@Param("userId") String userId,@Param("jobId")String jobId,@Param("status") String status);

  /** 订单报名情况*/
  List<HashMap> selectOrderApply(@Param("jobId")String jobId);

  /** 根据订单Id找发单人信息 */
  HashMap selectEmployer(@Param("jobId")String jobId);

  /** 定时器:获取上架订单列表（订单id+订单状态+开始时间+结束时间） */
  List<HashMap> selectTaskPartOrder();

  /** 定时器:获取进行中的订单列表（订单id+订单状态+开始时间+结束时间） */
  List<HashMap> selectBeginPartOrders();

  /** 定时器:设置订单状态 */
  void setPartOrderStatus(@Param("jobId") String jobId,@Param("status") int status);

  /** 定时器:查找订单劳工列表 */
  List<HashMap> selectPartOrderWorkers(String jobId);

  /** 定时器:更改劳工完成订单数量 */
  void updateWorkerFinishNum(String userId);

  /** 定时器:更改劳工报名表状态 待收款 */
  void updateWorkerApply(@Param("userId") String userId,@Param("jobId") String jobId,@Param("status") int status);

  /** 定时器:更改用户报名表中工资 */
  void updateWorkerApplyWages(@Param("userId") String userId,@Param("jobId") String jobId,@Param("wages") BigDecimal wages);

  /** 增加订单报名人数数量 */
  void addPartOrderApplyNum(String jobId);

  /** 增加订单劳工人数数量*/
  void addPartOrderWorkerNum(String jobId);
  /** 减少订单劳工人数数量*/
  void reducePartOrderWorkerNum(String jobId);
  /** 增加领队人数*/
  void addPartOrderLeaderNum(String jobId);

  /** 增加招聘方发单数量 */
  void addEmployerOrderNum(String userId);

  /** 根据jobId查找待开始订单 */
  HashMap selectTaskPartOrderByJobId(String jobId);

  /** 订单总额更新 */
  void updatePartOrderAmount(@Param("jobId") String jobId,@Param("amount") BigDecimal amount);

  /** 订单跟踪-订单信息*/
  HashMap selectOrderHeader(String jobId);

  /** 订单跟踪-报名信息 */
  List<HashMap> selectOrderApplyInfo(String jobId);

  /** 订单跟踪-交易信息 */
  List<HashMap> selectOrderPayInfo(String jobId);
  /** 订单跟踪-劳工信息 */
  List<HashMap> selectOrderWorkerInfo(String jobId);
  /** 更新用户签到时间*/
  void updateWorkerSignIn(@Param("jobId") String jobId,@Param("userId") String userId);
  /** 更新用户签退时间*/
  void updateWorkerSignOut(String jobId,String userId);
  /** 更新用户签到状态*/
  void updateWorkerSignStatus(@Param("jobId") String jobId,@Param("userId") String userId,@Param("status") int status);
  /** 报名统计 */
  HashMap countOrderApply(String jobId);

  List<HashMap> selectJobs(String jobName);
  List<HashMap> selectMyJobs(@Param("jobName") String jobName,@Param("userId")String userId);

}
