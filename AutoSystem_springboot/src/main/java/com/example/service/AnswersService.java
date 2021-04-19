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
}