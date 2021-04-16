package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.HistoryEntity;

/**
 * 採点結果得点履歴 Repository
 */
@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity,Long> {}