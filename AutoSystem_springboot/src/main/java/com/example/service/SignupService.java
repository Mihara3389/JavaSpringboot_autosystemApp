package com.example.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cheack.SignupuserCheack;
import com.example.dto.SignupRequest;
import com.example.entity.UserEntity;
import com.example.repository.UserRepository;

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
  private UserRepository userRepository;
  
  @Autowired
  PasswordEncoder passwordEncoder;

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
  public void create(SignupRequest userRequest) {

	  //ユーザーチェック用のクラス呼び出し
	  SignupuserCheack cheack = new SignupuserCheack();
	  boolean usercheack = cheack.isEmptyByUsername(userRequest.getUsername());
	  
	  if(usercheack==false){
		  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		  UserEntity user = new UserEntity();
		  
		  user.setUsername(userRequest.getUsername());
		  //パスワードをハッシュ化して渡すオブジェクトにセット。
		  user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		  user.setCreatedAt(timestamp);
		  user.setUpdatedAt(timestamp);
		  userRepository.save(user);
	  }else {
		  
	  }
  }
}