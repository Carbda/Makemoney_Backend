package com.backend.makemoney.controller.common;

import com.backend.makemoney.entity.Question;
import com.backend.makemoney.service.QuestionService;
import com.backend.makemoney.utils.Result;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
  @Resource private QuestionService questionService;

  /**
   * 得到题目列表
   *
   * @return
   */
  @CrossOrigin
  @PostMapping("/getQuestionList")
  public Result getQuestionList(@RequestBody Map map) {
    int pageNum = (int) map.get("pageNum");
    int pageSize = (int) map.get("pageSize");
    String questionId = (String) map.get("questionId");
    String questionContent = (String) map.get("questionContent");
    String questionScore = (String) map.get("questionScore");
    return Result.success(
        "题目列表",
        questionService.getQuestions(
            pageNum, pageSize, questionId, questionContent, questionScore));
  }

  /**
   * 添加题目
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping("/addQuestion")
  public Result addQuestion(@RequestBody Map map) {
    String questionContent = (String) map.get("questionContent");
    String questionOptions = (String) map.get("questionOptions");
    String questionAnswer = (String) map.get("questionAnswer");
    String questionScore = (String) map.get("questionScore");
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
    String questionId = simpleDateFormat.format(date);
    Question question =
        new Question(questionId, questionContent, questionOptions, questionAnswer, questionScore);
    questionService.insertQuestion(question);
    return Result.success("题目[" + questionId + "]添加成功！");
  }

  /**
   * 编辑题目
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping("/updateQuestion")
  public Result updateQuestion(@RequestBody Map map) {
    String questionId = (String) map.get("questionId");
    String questionContent = (String) map.get("questionContent");
    String questionOptions = (String) map.get("questionOptions");
    String questionAnswer = (String) map.get("questionAnswer");
    String questionScore = (String) map.get("questionScore");
    Question question =
        new Question(questionId, questionContent, questionOptions, questionAnswer, questionScore);
    questionService.updateQuestion(question);
    return Result.success("编辑题目[" + questionId + "]成功！");
  }

  /**
   * 删除题目
   *
   * @param map
   * @return
   */
  @CrossOrigin
  @PostMapping("/deleteQuestion")
  public Result deleteQuestion(@RequestBody Map map) {
    String questionId = (String) map.get("questionId");
    questionService.deleteQuestion(questionId);
    return Result.success("删除题目[" + questionId + "]成功！");
  }

  /**
   * 生成答题试卷
   *
   * @param topicNum
   * @return
   */
  @CrossOrigin
  @GetMapping("/generateTest")
  public Result deleteQuestion(@RequestParam int topicNum) {
    List<Question> test = questionService.generateTest(topicNum);
    if (test != null) return Result.success("组卷成功！试题数目[" + topicNum + "]", test);
    else return Result.error(500, "题目数量超出题库大小");
  }

  @CrossOrigin
  @PostMapping("/addTestHistory")
  public Result addTestHistory(@RequestBody Map map) {
    String userId = (String) map.get("userId");
    int score = (int) map.get("score");
    questionService.insertTestHistory(userId, score);
    if ( score>= 90) {
      questionService.updateUserLeader(userId);
      return Result.success("用户【" + userId + "】成为领队！");
    } else return Result.success("用户答题记录已添加！用户id：" + userId + " 得分：" + score);
  }

  @CrossOrigin
  @PostMapping(value = "/getQuestionSum")
  public Result getQuestionSum(@RequestBody Map map){
    String questionId = (String) map.get("questionId");
    String questionContent = (String) map.get("questionContent");
    String questionScore = (String) map.get("questionScore");
    HashMap<String, Object> result = new HashMap<>();
    result.put("sum",questionService.getQuestionsSum(questionId,questionContent, questionScore));
    return Result.success("获取题目数量",result);
  }
  @CrossOrigin
  @GetMapping("/getTestHistory")
  public Result getTestHistory(@RequestParam String userId) {
    return Result.success("用户["+userId+"]答题记录",questionService.getQuestionHistory(userId));
  }
}
