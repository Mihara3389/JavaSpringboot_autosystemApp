package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.AnswersEntity;

/**
 * 答え Repository
 */
@Repository
public interface AnswersRepository extends JpaRepository<AnswersEntity,Integer> {
	//データベースに該当する答えを探す
	AnswersEntity findByAnswerEquals(String answer);
	//データベースに一致するIdを探す
	AnswersEntity findByIdEquals(Integer id);
	//データベースに一致する質問Idを探す
	List<AnswersEntity> findByQuestionIdEquals(Integer questionId);
}