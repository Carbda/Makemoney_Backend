package com.backend.makemoney.service.Impl;

import com.backend.makemoney.entity.Question;
import com.backend.makemoney.mapper.QuestionMapper;
import com.backend.makemoney.service.QuestionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  @Resource private QuestionMapper questionMapper;
  @Override
  public void insertQuestion(Question question) {
    questionMapper.insertQuestion(question);
  }

  @Override
  public List<Question> getQuestions(int pageNum,int pageSize,String questionId,String questionContent,String questionScore) {
    pageNum = (pageNum - 1) * pageSize;
    return questionMapper.getQuestions(pageNum,pageSize,questionId,questionContent,questionScore);
  }

  @Override
  public void deleteQuestion(String questionId) {
    questionMapper.deleteQuestion(questionId);
  }

  @Override
  public void updateQuestion(Question question) {
    questionMapper.updateQuestion(question);
  }

  @Override
  public List<Question> generateTest(int topicNum) {
    List<Question> questions = questionMapper.selectAllQuestions();
    List<Question> test=new ArrayList<>();
    int length =questions.size();
    if(topicNum>length) return null;
    for(int i=0 ; i <topicNum;i++){
      int r=(int)(Math.random()*length);
//      System.out.println(r);
     test.add(questions.get(r));
//      System.out.println(questions.get(r));
     questions.set(r,questions.get(length-1));
      length--;
    }
    return test;
  }

  /**
   * 添加用户答题记录
   * @param userId
   * @param score
   */
  @Override
  public void insertTestHistory(String userId, int score) {
    questionMapper.insertTestHistory(userId,score);
  }

  @Override
  public void updateUserLeader(String userId) {
    questionMapper.updateUserLeader(userId);
  }

  @Override
  public int getQuestionsSum(String questionId,String questionContent,String questionScore){
    return questionMapper.getQuestionsSum(questionId, questionContent, questionScore);
  }

  @Override
  public List<HashMap> getQuestionHistory(String userId) {
    return questionMapper.selectQuestionHistory(userId);
  }

}
