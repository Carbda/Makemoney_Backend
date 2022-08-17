package com.backend.makemoney.mapper;

import com.backend.makemoney.entity.Question;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionMapper {
  void insertQuestion(Question question);
  List<Question>  getQuestions(
      @Param("pageNum") int pageNum,
      @Param("pageSize") int pageSize,
      @Param("questionId") String questionId,
      @Param("questionContent") String questionContent,
      @Param("questionScore") String questionScore );
  void deleteQuestion(String questionId);
  void updateQuestion(Question question);
  List<Question> selectAllQuestions();
  void insertTestHistory(@Param("userId") String userId,@Param("score") int score);
  void updateUserLeader(String userId);
  int getQuestionsSum(
    @Param("questionId") String questionId,
    @Param("questionContent") String questionContent,
    @Param("questionScore") String questionScore );
  List<HashMap> selectQuestionHistory(String userId);
}
