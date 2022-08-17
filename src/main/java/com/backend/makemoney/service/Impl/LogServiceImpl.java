package com.backend.makemoney.service.Impl;

import com.backend.makemoney.mapper.LogMapper;
import com.backend.makemoney.service.LogService;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
  @Resource
  private LogMapper logMapper;
  @Override
  public void addLog(String logCreateBy,String type, String operationContent, char operationResult) {
    logMapper.addLog(logCreateBy,type,operationContent,operationResult);
  }
}
