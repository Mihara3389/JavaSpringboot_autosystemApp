package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.HistoryEntity;

/**
 * 採点結果得点履歴 Repository
 */
@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
    @Query("SELECT DISTINCT h FROM HistoryEntity h INNER JOIN h.userEntity WHERE h.userEntity.id = :id ORDER BY h.user_id")
    List<HistoryEntity> find(@Param("id") Integer id);
}