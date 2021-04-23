package com.example.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.ListForm;
import com.example.dto.ResultForm;
import com.example.dto.TestForm;
import com.example.entity.AnswersEntity;
import com.example.entity.HistoryEntity;
import com.example.entity.QuestionsEntity;
import com.example.entity.UserEntity;
import com.example.service.AnswersService;
import com.example.service.HistoryService;
import com.example.service.QuestionsService;

	/**
	 * Top Controller
	 */
	@Controller
	@RequestMapping(value="/top")
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
	/**
	 * 問題一覧を表示
	 * @param model Model
	 * @return 問題一覧
	 */
	@RequestMapping(method=RequestMethod.POST,params="action=list")
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
	/**
	 * テストを表示
	 * @param model Model
	 * @return テスト
	 */
	@RequestMapping(method=RequestMethod.POST,params="action=test")
	public String postTest(Model model) {
		//質問を取得
		List<QuestionsEntity> questions = questionsService.searchAll();
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
	@RequestMapping(method=RequestMethod.POST,params="action=history")
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

	/**
	 * 採点結果を表示
	 * @param model Model
	 * @return テスト
	 */
	@RequestMapping(value="/test", method=RequestMethod.POST)
	public String postTestCheack(@ModelAttribute("testForm") TestForm testForm,@ModelAttribute("resultForm") ResultForm resultForm,Model model) {
		//カウンターの宣言と初期化
		double question_count=0;
		double correct_count=0;
		//現在時刻取得
		Timestamp currenTime = new Timestamp(System.currentTimeMillis());
		//取得した問題をリストへ置き換える
		String testForm_questions_id =testForm.getQuestions_id();
		List<String> form_questions_id = Arrays.asList(testForm_questions_id.split(","));
		String testForm_answer =testForm.getAnswer();
		List<String> form_answer = Arrays.asList(testForm_answer.split(","));
		//DBの答えを取得
		List<AnswersEntity> answers = answersService.searchAll();
		question_count=form_questions_id.size();
		//採点チェック
		//答え分ループ
		for(int i = 0; i < form_answer.size(); i++){
			//int型を変換させる
			int formId = Integer.parseInt(form_questions_id.get(i));
			String formAnswer = form_answer.get(i);
			//nullチェック
			if (formAnswer == null) { 
				continue; 
			}
			//答え分ループ
			for(int j = 0; j < answers.size(); j++){
				//問題のidと答えの問題idが一致したら
				int answerId = answers.get(j).getQuestionId();
				String answerAnswer = answers.get(j).getAnswer();
				//nullチェック
				AnswersEntity answerEntity = answers.get(j); 
				if (answerEntity == null) { 
					continue; 
				}
				if(formId == answerId){
					//答えの内容が一致したら
					if(formAnswer.equals(answerAnswer)) {
						correct_count = correct_count + 1;
						break;
					}
				}
			}
		}
		//採点を結果（小数点以下四捨五入）
		long result = Math.round(((correct_count/question_count)*100));
		resultForm.setQuestioncount((int) question_count);
		resultForm.setCorrectcount((int) correct_count);
		resultForm.setResulttest((int) result);
		resultForm.setCurrentime(currenTime);
		//テスト結果画面へ
		model.addAttribute("resultForm", resultForm);
		return "testCheack";
		}
}