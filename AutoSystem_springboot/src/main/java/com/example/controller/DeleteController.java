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

import com.example.Common;
import com.example.dto.ListForm;
import com.example.dto.ReturnlistForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

	/**
	 * 削除データ Controller
	 */
	@Controller
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public class DeleteController{
	/**
	 * 削除データ Service
	 */
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private AnswersService answersService;
	
	
	
	/**
	 * 問題一覧画面へ戻る
	 * @param model Model
	 * @return 
	 * @return 削除
	 */
	@RequestMapping(params="action=return")
	public String toRedirectList(@ModelAttribute("listForm") ArrayList<ListForm>listForm,Model model) {
		//質問・答えをDBから取得
		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
		List<AnswersEntity> answerEntity = answersService.searchAll();
		//質問がDBに一軒もなかったら、新規登録画面へ
		if(questionsEntity.isEmpty()) 
		{
			//問題・答えの新規登録画面へforward
			return "register";
		}else 
		{
			//問題を取得
			Common com = new Common();
			listForm = com.toCommon(questionsEntity, answerEntity, listForm);
		}
		model.addAttribute(listForm);
		//問題一覧画面へ戻る
		return "list";
	    }
	/**
	 * データを削除し問題一覧画面へ戻る
	 * @param model Model
	 * @return 問題一覧
	 */
	@RequestMapping(params="action=delete")
	public String deleteReturn(@ModelAttribute("rtltForm") ReturnlistForm rtltForm, @ModelAttribute("listForm") ArrayList<ListForm>listForm, Model model) {
		//削除対象の質問idを取得し削除する
		int question_id =rtltForm.getId();
		questionsService.deleteQuestion(question_id);
		//答えを順に削除していく
		String rtltForm_answer_id = rtltForm.getAnswer_id();
		List<String> form_answer_id  = new ArrayList<String>();
		if(rtltForm_answer_id != null) {
			form_answer_id = Arrays.asList(rtltForm_answer_id.split(","));
			for(int i = 0; i < form_answer_id.size(); i++) {
				String answer_id = form_answer_id.get(i);
				int int_answer_id = Integer.parseInt(answer_id);
				answersService.deleteAnswer(int_answer_id);
			}
		}
		//質問・答えをDBから取得
		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
		List<AnswersEntity> answerEntity = answersService.searchAll();
		//質問がDBに一件もなかったら、新規登録画面へ
		if(questionsEntity.isEmpty()) 
		{
			//問題・答えの新規登録画面へforward
			return "register";
		}else 
		{
			//問題を取得
			Common com = new Common();
			listForm = com.toCommon(questionsEntity, answerEntity, listForm);
		}
		model.addAttribute(listForm);
		//問題一覧画面へ戻る
		return "list";
	    }
	}