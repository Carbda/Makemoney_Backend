package com.backend.makemoney.entity;

import lombok.Data;

@Data
public class Question {
  String questionId;
  String questionContent;
  String questionOptions;
  String questionAnswer;
  String questionScore; public Question(String questionId, String questionContent, String questionOptions,
      String questionAnswer, String questionScore) {
    this.questionId = questionId;
    this.questionContent = questionContent;
    this.questionOptions = questionOptions;
    this.questionAnswer = questionAnswer;
    this.questionScore = questionScore;
  }
}
