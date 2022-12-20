package com.ss.training.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ss.training.domain.TransactionHeader;


public interface TransactionHeaderService {
  TransactionHeader findById(Long id);
  void insertTransactionHeader(TransactionHeader json);
}   

