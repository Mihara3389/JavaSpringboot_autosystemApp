package com.example.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.QuestionsEntity;
import com.example.service.QuestionsService;

	/**
	 * テスト Controller
	 */
	@Controller
	public class QuestionsController {
	/**
	 * テスト Service
	 */
	@Autowired
	private QuestionsService questionsService;

	/**
	 * テストを表示
	 * @param model Model
	 * @return テスト
	 */
	@RequestMapping(value="/top", method=RequestMethod.POST,params="action=test")
	public String postTest(Model model) {
		//質問を取得
		List<QuestionsEntity> questions = questionsService.searchAll();
		//リストの中身をシャッフル
		Collections.shuffle(questions);
		//テスト画面へ
		model.addAttribute("questions", questions);
		return "test";
	}
	}