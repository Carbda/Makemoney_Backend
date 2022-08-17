package com.backend.makemoney.service;

import com.backend.makemoney.entity.Question;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
  void insertQuestion(Question question);

  List<Question> getQuestions(int pageNum,int pageSize,String questionId,String questionContent,String questionScore);

  void deleteQuestion(String questionId);

  void updateQuestion(Question question);

  List<Question> generateTest(int topicNum);

  void insertTestHistory(String userId,int score);

  void updateUserLeader(String userId);

  int getQuestionsSum(String questionId,String questionContent,String questionScore);

  List<HashMap> getQuestionHistory(String userId);
}
