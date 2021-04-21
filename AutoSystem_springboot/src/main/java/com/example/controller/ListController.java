package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.ListForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

	/**
	 * 問題一覧 Controller
	 */
	@Controller
	public class ListController {
	/**
	 * 問題一覧 Service
	 */
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private AnswersService answersService;
	/**
	 * 問題一覧を表示
	 * @param model Model
	 * @return 問題一覧
	 */
	@RequestMapping(value="/top", method=RequestMethod.POST,params="action=list")
	public String postHistorylist(@ModelAttribute("listForm") ArrayList<ListForm>listForm,Model model) {
		//質問・答えをDBから取得
		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
		List<AnswersEntity> answerEntity = answersService.searchAll();
		//変数定義
		int db_questionsId =0;
		int db_answersId =0;
		int count =0;
		//questionsループ
		for(int i = 0; i < questionsEntity.size(); i++) {
			//カウント初期化
			count = 0;
			if(questionsEntity.isEmpty()) {
				//問題・答えの新規登録画面へforward
				return "register";
			}else {
				//問題を取得
				db_questionsId = questionsEntity.get(i).getId();
				//answersループ
				for(int j = 0; j < answerEntity.size(); j++) {
					//nullチェック
					AnswersEntity answer = answerEntity.get(j); 
					if (answer == null) { 
						continue; 
					}
					//照合
					db_answersId = answerEntity.get(j).getQuestion_id();
					if(db_questionsId == db_answersId) {
						//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
						ListForm list = new ListForm();
						//同じ問題IDの答え数
						count = count+1;
						//値をつめる
						list.setId(db_questionsId);
						list.setQuestion(questionsEntity.get(i).getQuestion());
						list.setAnswer_id(count);	
						list.setAnswer(answerEntity.get(j).getAnswer());	
						//リストへつめる
						listForm.add(list);
					}
				}
			}
		}
		model.addAttribute("listForm", listForm);
		return "list";
	}
	}