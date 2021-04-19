package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.HistoryEntity;
import com.example.repository.HistoryRepository;

/**
 * 採点結果履歴画面 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class HistoryService {

  /**
   * 採点結果履歴画面 Repository
   */
  @Autowired
  private HistoryRepository historyRepository;

  /**
   * 採点結果履歴画面 全検索
   * @return 検索結果
   */
  public List<HistoryEntity> searchAll() {
    return historyRepository.findAll();
  }
}