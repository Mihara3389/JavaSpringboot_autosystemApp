package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.QuestionsEntity;

/**
 * 質問 Repository
 */
@Repository
public interface QuestionsRepository extends JpaRepository<QuestionsEntity,Integer> {
	//データベースより一致するものを探す
	QuestionsEntity findByQuestionEquals(String question);
	//データベースより一致するものを探す
	QuestionsEntity findByIdEquals(Integer id);
}