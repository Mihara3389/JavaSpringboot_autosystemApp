package com.example.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.SignupRequest;
import com.example.entity.SignupEntity;
import com.example.repository.SignupRepository;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class SignupService {

  /**
   * ユーザー情報 Repository
   */
  @Autowired
  private SignupRepository signupRepository;

  /**
   * ユーザー情報 全検索
   * @return 検索結果
   */
  public List<SignupEntity> searchAll() {
    return signupRepository.findAll();
  }

  /**
   * ユーザー情報 新規登録
   * @param user ユーザー情報
   */
  public void create(SignupRequest userRequest) {
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	  
	SignupEntity user = new SignupEntity();
    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setCreatedAt(timestamp);
    user.setUpdatedAt(timestamp);
    signupRepository.save(user);
  }
}