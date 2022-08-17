package com.backend.makemoney.service.Impl;

import com.backend.makemoney.entity.PartOrder;
import com.backend.makemoney.entity.vo.OrderAuthList;
import com.backend.makemoney.mapper.OrderMapper;
import com.backend.makemoney.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  @Resource private OrderMapper orderMapper;

  @Override
  public List<PartOrder> getAllPartOrderbyPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<PartOrder> partOrderList = orderMapper.findAll();
    PageInfo<PartOrder> partOrderPageInfo = new PageInfo<>(partOrderList);
    return partOrderPageInfo.getList();
  }

  @Override
  public List<PartOrder> getAllPartOrder() {
    return orderMapper.findAll();
  }

  /**
   * 订单管理-兼职订单
   *
   * @return
   */
  @Override
  public List<HashMap> getOnPartOrder() {
    return orderMapper.selectOnPartOrder();
  }

  /**
   * 获取兼职审核订单列表
   *
   * @param partOrderAuthList
   * @return
   */
  @Override
  public List<OrderAuthList> getPartOrderAuthList(OrderAuthList partOrderAuthList) {
    return orderMapper.selectPartOrderAuthList(partOrderAuthList);
  }

  /**
   * 获取全职审核订单列表
   *
   * @return
   */
  @Override
  public List<OrderAuthList> getFullOrderAuthList(OrderAuthList fullOrderAuthList) {
    return orderMapper.selectFullOrderAuthList(fullOrderAuthList);
  }

  /**
   * app获取兼职单详情+订单推荐+用户报名信息
   *
   * @param jobId
   * @return
   */
  @Override
  public Map getPartOrderInfo(String jobId, String label, Integer pageNum, String userId) {
    Map partOrderInfo = new HashMap();
    //System.out.println(orderMapper.selectPartOrder(jobId));
    partOrderInfo.put("employer",orderMapper.selectEmployer(jobId));
    partOrderInfo.put("partOrder", orderMapper.selectPartOrder(jobId));
    pageNum = (pageNum - 1) * 10;
    String[] labels = label.split(",");
    Set recommends = new HashSet();
    for (String s : labels) {
      recommends.addAll(orderMapper.selectRecommendPartOrder(s, pageNum));
    }
    partOrderInfo.put("recommend", recommends);
    partOrderInfo.put("userApply", orderMapper.selectUserApply(jobId, userId));
    return partOrderInfo;
  }

  /**
   * app 获取招聘方首页数据（轮播图+发单列表）
   *
   * @return
   */
  @Override
  public Map getEmployerIndex(String userId) {
    Map employerIndex = new HashMap();
    employerIndex.put("bannerList", orderMapper.selectAdvertise());
    employerIndex.put("jobList", orderMapper.selectEmployerIndex(userId));
    return employerIndex;
  }

  /**
   * app 获取劳工首页数据（轮播图+订单列表）
   *
   * @param pageNum
   * @return
   */
  @Override
  public Map getWorkerIndex(Integer pageNum) {
    Map workerIndex = new HashMap();
    pageNum = (pageNum - 1) * 10;
    workerIndex.put("bannerList", orderMapper.selectAdvertise());
    workerIndex.put("jobList", orderMapper.selectWorkerIndex(pageNum));
    return workerIndex;
  }

  /**
   * 创建兼职订单
   *
   * @param partOrder
   */
  @Override
  public void createPartOrder(PartOrder partOrder) {
    orderMapper.insertPartOrder(partOrder);
  }

  @Override
  public void closePartOrder(String jobId) {
    orderMapper.closePartOrder(jobId);
  }

  /**
   * 获取劳工接单列表
   *
   * @param userId
   * @return
   */
  @Override
  public List<HashMap> getWorkerOrder(String userId) {
    return orderMapper.selectWorkerOrder(userId);
  }

  /**
   * 获取招聘方发单列表
   *
   * @param userId
   * @return
   */
  @Override
  public List<HashMap> getEmployerOrder(String userId) {
    return orderMapper.selectEmployerOrder(userId);
  }

  /**
   * 获取审核招聘订单详情
   *
   * @param jobId
   * @return
   */
  @Override
  public HashMap getAuthPartOrder(String jobId) {
    return orderMapper.selectAuthPartOrder(jobId);
  }

  /**
   * 通过id找兼职订单
   *
   * @param jobId
   * @return
   */
  @Override
  public boolean selectOrderById(String jobId) {
    if ((orderMapper.selectOrderById(jobId) == null)) return false;
    else return true;
  }

  /**
   * 添加用户报名信息
   *
   * @param userId
   * @param jobId
   * @param type
   * @param status
   * @param auditMessage
   */
  @Override
  public void insertJobApply(
      String userId, String jobId, String type, String status, String auditMessage) {
    orderMapper.insertJobApply(userId,jobId,type,status,auditMessage);
  }

  @Override
  public void cancelOrder(String userId, String jobId) {
    orderMapper.cancelOrder(userId,jobId);
  }

  @Override
  public void hireWorker(String userId, String jobId, String status) {
    orderMapper.hireWorker(userId,jobId,status);
  }

  @Override
  public List<HashMap> selectOrderApply(String jobId) {
    return orderMapper.selectOrderApply(jobId);
  }

  @Override
  public List<HashMap> selectTaskOrderList() {
    return orderMapper.selectTaskPartOrder();
  }

  /** 订单完成 */
  @Override
  public void completePartOrder(String jobId) {
    orderMapper.setPartOrderStatus(jobId,3);
  }

  /** 订单开始 */
  @Override
  public void beginPartOrder(String jobId) {
    orderMapper.setPartOrderStatus(jobId,2);
  }

  /** 订单待支付 */
  @Override
  public void needPayPartOrder(String jobId) {
    orderMapper.setPartOrderStatus(jobId,5);
  }

  /** 取消订单 */
  @Override
  public void cancelPartOrder(String jobId) {
    orderMapper.setPartOrderStatus(jobId,4);
  }

  @Override
  public List<HashMap> selectPartOrderWorkers(String jobId) {
    return orderMapper.selectPartOrderWorkers(jobId);
  }

  @Override
  public void updateWorkerFinishNum(String userId) {
    orderMapper.updateWorkerFinishNum(userId);
  }

  @Override
  public void updateWorkerApply(String userId, String jobId,int status) {
    orderMapper.updateWorkerApply(userId,jobId,status);
  }

  @Override
  public List<HashMap> selectBeginPartOrders() {
    return orderMapper.selectBeginPartOrders();
  }

  @Override
  public void updateWorkerApplyWages(String userId, String jobId, BigDecimal wages) {
    orderMapper.updateWorkerApplyWages(userId,jobId,wages);
  }

  @Override
  public void addPartOrderApplyNum(String jobId) {
    orderMapper.addPartOrderApplyNum(jobId);
  }

  @Override
  public PartOrder selectPartOrder(String jobId) {
    return orderMapper.selectPartOrder(jobId);
  }

  @Override
  public void addPartOrderWorkerNum(String jobId) {
  orderMapper.addPartOrderWorkerNum(jobId);
  }

  @Override
  public void reducePartOrderWorkerNum(String jobId) {
    orderMapper.reducePartOrderWorkerNum(jobId);
  }

  @Override
  public HashMap selectTaskPartOrderByJobId(String jobId) {
    return orderMapper.selectTaskPartOrderByJobId(jobId);
  }

  @Override
  public HashMap selectUserApply(String jobId, String userId) {
    return orderMapper.selectUserApply(jobId,userId);
  }

  @Override
  public void addPartOrderLeaderNum(String jobId) {
    orderMapper.addPartOrderLeaderNum(jobId);
  }

  @Override
  public Map selectOrderApplyInfo(String jobId) {
    Map map= new HashMap();
    map.put("registrationCount",orderMapper.countOrderApply(jobId));
    map.put("registrationList",orderMapper.selectOrderApplyInfo(jobId));
    return map;
  }

  @Override
  public List<HashMap> selectOrderPayInfo(String jobId) {
 return orderMapper.selectOrderPayInfo(jobId);
  }

  @Override
  public List<HashMap> selectOrderWorkerInfo(String jobId) {
return orderMapper.selectOrderWorkerInfo(jobId);
  }

  @Override
  public HashMap selectOrderHeader(String jobId) {
return orderMapper.selectOrderHeader(jobId);
  }

  @Override
  public void updateWorkerSignIn(String jobId, String userId) {
orderMapper.updateWorkerSignIn(jobId,userId);
  }

  @Override
  public void updateWorkerSignOut(String jobId, String userId) {
orderMapper.updateWorkerSignOut(jobId,userId);
  }

  @Override
  public void updateWorkerSignStatus(String jobId, String userId, int status) {
    orderMapper.updateWorkerSignStatus(jobId,userId,status);
  }

  @Override
  public List<HashMap> selectJobs(String jobName) {
    return orderMapper.selectJobs(jobName);
  }

  @Override
  public List<HashMap> selectMyJobs(String jobName,String userId) {
    return orderMapper.selectMyJobs(jobName,userId);
  }
}
