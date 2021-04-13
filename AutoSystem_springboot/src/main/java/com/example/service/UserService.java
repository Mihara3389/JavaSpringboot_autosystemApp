package com.example.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserRequest;
import com.example.entity.UserEntity;
import com.example.repository.UserRepository;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {

  /**
   * ユーザー情報 Repository
   */
  @Autowired
  private UserRepository userRepository;

  /**
   * ユーザー情報 全検索
   * @return 検索結果
   */
  public List<UserEntity> searchAll() {
    return userRepository.findAll();
  }

  /**
   * ユーザー情報 新規登録
   * @param user ユーザー情報
   */
  public void create(UserRequest userRequest) {
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	  
    UserEntity user = new UserEntity();
    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setCreatedAt(timestamp);
    user.setUpdatedAt(timestamp);
    userRepository.save(user);
  }
}