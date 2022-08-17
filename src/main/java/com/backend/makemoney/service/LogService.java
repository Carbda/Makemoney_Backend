package com.backend.makemoney.service;

import org.springframework.stereotype.Service;

@Service
public interface LogService {
  void addLog(
      String logCreateBy,
      String type,
      String operationContent,
      char operationResult
  );
}
