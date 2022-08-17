package com.backend.makemoney.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface LogMapper{
  void addLog(
      @Param("logCreateBy") String logCreateBy,
      @Param("type") String type,
      @Param("operationContent") String operationContent,
      @Param("operationResult") char operationResult
  );
}
