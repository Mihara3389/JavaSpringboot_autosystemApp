package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.DeleteForm;
import com.example.dto.DeleteObject;
import com.example.dto.ListForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

	/**
	 * 削除データ Controller
	 */
	@Controller
	public class DeleteController {
	/**
	 * 削除データ Service
	 */
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private AnswersService answersService;
	
	/**
	 * 削除データを表示
	 * @param model Model
	 * @return 削除
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST,params="action=delete")
	public String postDelete(@ModelAttribute("deleteObject") DeleteObject deleteObject,@ModelAttribute("deleteForm") ArrayList<DeleteForm> deleteForm,Model model) {
		//問題idを取得
		int questionId =deleteObject.getId();
		//取得した問題idを使用して問題と答えを取得する
		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
		List<AnswersEntity> answerEntity = answersService.searchAll();
		//変数定義
		int db_questionsId =0;
		int db_answersId =0;
		int db_answers_QuestionId=0;
		int count =0;
		//questionsループ
				for(int i = 0; i < questionsEntity.size(); i++) {
					if(questionsEntity.isEmpty()) {
						continue;
					}else {
						//問題を取得
						db_questionsId = questionsEntity.get(i).getId();
						if(db_questionsId==questionId) {
						//answersループ
						for(int j = 0; j < answerEntity.size(); j++) {
							//nullチェック
							AnswersEntity answer = answerEntity.get(j); 
							if (answer == null) { 
								continue; 
							}
							//照合
							db_answersId = answerEntity.get(j).getId();
							db_answers_QuestionId = answerEntity.get(j).getQuestion_id();
							if(db_questionsId == db_answers_QuestionId) {
								//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
								DeleteForm deletelist = new DeleteForm();
								//同じ問題IDの答え数
								count = count+1;
								//値をつめる
								deletelist.setId(db_questionsId);
								deletelist.setQuestion(questionsEntity.get(i).getQuestion());
								deletelist.setAnswer_count(count);
								deletelist.setAnswer_id(Integer.toString(db_answersId));	
								deletelist.setAnswer(answerEntity.get(j).getAnswer());	
								//リストへつめる
								deleteForm.add(deletelist);
							}
						}
						}
					}
				}
				model.addAttribute("deleteForm", deleteForm);
				return "delete";
			}
	/**
	 * 問題一覧画面へ戻る
	 * @param model Model
	 * @return 削除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST,params="action=return")
	public String toRedirectList(@ModelAttribute("listForm") ArrayList<ListForm>listForm,Model model) {
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
	/**
	 * データを削除し問題一覧画面へ戻る
	 * @param model Model
	 * @return 問題一覧
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST,params="action=delete")
	public String deleteReturn(@ModelAttribute("deleteForm") DeleteForm deleteForm, @ModelAttribute("listForm") ArrayList<ListForm>listForm,Model model) {
		//取得した答えをリストへ置き換える
		String deleteForm_answer_id = deleteForm.getAnswer_id();
		List<String> form_answer_id = Arrays.asList(deleteForm_answer_id.split(","));
		//削除対象の質問idを取得し削除する
		int question_id =deleteForm.getId();
		questionsService.deleteQuestion(question_id);
		//答えを順に削除していく
		for(int i = 0; i < form_answer_id.size(); i++) {
			String answer_id = form_answer_id.get(i);
			int int_answer_id = Integer.parseInt(answer_id);
			answersService.deleteAnswer(int_answer_id);
		}
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
		//問題一覧画面へ戻る
		return "list";
	    }
	}