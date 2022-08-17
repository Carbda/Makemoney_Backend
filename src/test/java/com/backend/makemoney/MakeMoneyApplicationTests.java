package com.backend.makemoney;

import com.backend.makemoney.service.QuestionService;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MakeMoneyApplicationTests {
  @Resource private QuestionService questionService;
  @Test
  void contextLoads() {
    System.out.println(questionService.generateTest(3));
    }
  }