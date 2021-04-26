package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.AnswersEntity;
import com.example.repository.AnswersRepository;

/**
 * 答え Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class AnswersService {

  /**
   * 答え Repository
   */
 @Autowired
  private AnswersRepository answersRepository;

  /**
   * 答え 全検索
   * @return 検索結果
   */

  public List<AnswersEntity> searchAll() {
    return answersRepository.findAll();
  }
  /**
   * 答え 新規登録
 * @return 
   * 
   */
  public AnswersEntity saveAnswer(String answer) {
  	AnswersEntity a = answersRepository.findByAnswerEquals(answer);
  	return a;
  }
  /**
   * 答え 削除
   * 
   */
  public void deleteAnswer(Integer id) {
	  answersRepository.deleteById(id);
  }

}