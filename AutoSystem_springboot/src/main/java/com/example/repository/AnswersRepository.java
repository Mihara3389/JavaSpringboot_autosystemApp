package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.AnswersEntity;

/**
 * 答え Repository
 */
@Repository
public interface AnswersRepository extends JpaRepository<AnswersEntity,Integer> {
	//データベースより一致するものを探す
	AnswersEntity findByAnswerEquals(String answer);
	//データベースより一致するものを探す
	AnswersEntity findByIdEquals(Integer id);
}