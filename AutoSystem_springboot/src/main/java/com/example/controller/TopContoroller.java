package com.example.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Common;
import com.example.dto.ListForm;
import com.example.entity.AnswersEntity;
import com.example.entity.HistoryEntity;
import com.example.entity.QuestionsEntity;
import com.example.entity.UserEntity;
import com.example.repository.AnswersRepository;
import com.example.service.AnswersService;
import com.example.service.HistoryService;
import com.example.service.QuestionsService;

	/**
	 * Top Controller
	 */
	@Controller
	@RequestMapping(value="/top",method=RequestMethod.POST)
	public class TopContoroller {
	/**
	 * Top Service
	 */
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private AnswersService answersService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private AnswersRepository answersRepository;

	/**
	 * 問題一覧を表示
	 * @param model Model
	 * @return 問題一覧
	 */
	@RequestMapping(params="action=list")
	public String postHistorylist(@ModelAttribute("listForm") ArrayList<ListForm>listForm,Model model) {
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
	 * テストを表示
	 * @param model Model
	 * @return テスト
	 */
	@RequestMapping(params="action=test")
	public String postTest(Model model) {
		//質問を取得
		List<QuestionsEntity> questionsAll = questionsService.searchAll();
		List<QuestionsEntity> questions = new ArrayList<QuestionsEntity>();
		//答えが存在するかチェック
		for(int i = 0; i < questionsAll.size(); i++) 
		{
			int qId = questionsAll.get(i).getId();
			QuestionsEntity q = new QuestionsEntity();
			List<AnswersEntity> a = answersRepository.findByQuestionIdEquals(qId);		
			if(a.isEmpty()) {
			}else {
				q.setId(qId);
				q.setQuestion(questionsAll.get(i).getQuestion());
				questions.add(q);
			}
		}
		//リストの中身をシャッフル
		Collections.shuffle(questions);		
		//テスト画面へ
		model.addAttribute("questions", questions);
		return "test";
	}
	/**
	 * 採点結果得点履歴を表示
	 * @param model Model
	 * @return 採点結果得点履歴
	 */
	@RequestMapping(params="action=history")
	public String postHistorylist(Model model) {
		//SpringSecurityに格納されているログイン中ユーザのIDを取得
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int loginId = ((UserEntity)principal).getId();
		//採点結果履歴を取得
		List<HistoryEntity> histories = historyService.searchAll();
		//採点結果履歴画面へ値を渡すためのリスト
		List<HistoryEntity> historyList = new ArrayList<HistoryEntity>();
		//同じユーザIDの履歴すべて取得するまでループ
		for(int i = 0; i < histories.size(); i++) {
			//リスト内のデータを取得
			int listId = histories.get(i).getUserid();
			//ログイン中のidと採点結果履歴DB内のuser_idと照合
			if(loginId == listId) {
				//一致するデータがある場合は、得点と登録日を取得
				historyList.add(histories.get(i));								
				}
		}
		model.addAttribute("historyList", historyList);
		return "history";
	}
}