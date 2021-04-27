package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.QuestionsEntity;
import com.example.repository.QuestionsRepository;

/**
 * 質問 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class QuestionsService {

  /**
   * 質問 Repository
   */
  @Autowired
  private QuestionsRepository questionsRepository;

  /**
   * 質問 全検索
   * @return 検索結果
   */
  public List<QuestionsEntity> searchAll() {
    return questionsRepository.findAll();
  }
  /**
   * 質問 新規登録
 * @return 
   * 
   */
  public QuestionsEntity saveQuestion(String question) {
	  QuestionsEntity q = questionsRepository.findByQuestionEquals(question);
  	return q;
  }
  /**
   * 質問 更新
   * 
   */
  public QuestionsEntity updateQuestion(QuestionsEntity q) {
	  return questionsRepository.save(q);
  }
  /**
   * 質問 削除
   * 
   */
  public void deleteQuestion(Integer id) {
	  questionsRepository.deleteById(id);
  }
}