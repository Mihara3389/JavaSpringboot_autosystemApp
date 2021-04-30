package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.ConfrimRequest;
import com.example.dto.ListForm;
import com.example.dto.ReturnlistForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.repository.AnswersRepository;
import com.example.repository.QuestionsRepository;

/**
 *	List Controller
 */
@Controller
@RequestMapping(value="/list",method=RequestMethod.POST)
public class ListController 
{
	/**
	 * List Service
	 */
	@Autowired
	private QuestionsRepository quesitonsRepository;
	@Autowired
	private AnswersRepository answersRepository;
  /**
   * 問題・答え新規登録画面を表示
   * @param model Model
   * @return 問題・答え新規登録画面
   */
   @RequestMapping(params="action=register")
   public String getSignUp(Model model) {
	   model.addAttribute("confrimRequest", new ConfrimRequest());
       // register.htmlに画面遷移
       return "register";
   } 
	/**
	 * 編集データを表示
	 * @param model Model
	 * @return 編集
	 */
	@RequestMapping(params="action=edit")
	public String postEdit(@ModelAttribute("listForm") ListForm listForm, Model model) 
	{
		//画面よりidを取得する
		List<ReturnlistForm> returnlist = new ArrayList<ReturnlistForm>();
		int questionId =listForm.getId();
		int count =0;
		//取得した問題idを使用して問題と答えを取得する
		QuestionsEntity q = quesitonsRepository.findByIdEquals(questionId);
		List<AnswersEntity> a = answersRepository.findByQuestionIdEquals(questionId);
		//問題を取得
		for(int j = 0; j < a.size(); j++)
		{
			//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
			ReturnlistForm list = new ReturnlistForm();
			//同じ問題IDの答え数
			count = count+1;
			//値をつめる
			list.setId(questionId);
			list.setQuestion(q.getQuestion());
			list.setAnswer_count(count);
			list.setAnswer_id(Integer.toString(a.get(j).getId()));	
			list.setAnswer(a.get(j).getAnswer());	
			//リストへつめる
			returnlist.add(list);
		}
		//編集画面へ
		model.addAttribute("rtltForm", returnlist);
		return "edit";
	}
	/**
	 * 削除データを表示
	 * @param model Model
	 * @return 削除
	 */
	@RequestMapping(params="action=delete")
	public String postDelete(@ModelAttribute("listForm") ListForm listForm, Model model) 
	{
		//画面よりidを取得する
		List<ReturnlistForm> returnlist = new ArrayList<ReturnlistForm>();
		int questionId =listForm.getId();
		int count =0;
		//取得した問題idを使用して問題と答えを取得する
		QuestionsEntity q = quesitonsRepository.findByIdEquals(questionId);
		List<AnswersEntity> a = answersRepository.findByQuestionIdEquals(questionId);
		//問題を取得
		for(int j = 0; j < a.size(); j++)
		{
			//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
			ReturnlistForm list = new ReturnlistForm();
			//同じ問題IDの答え数
			count = count+1;
			//値をつめる
			list.setId(questionId);
			list.setQuestion(q.getQuestion());
			list.setAnswer_count(count);
			list.setAnswer_id(Integer.toString(a.get(j).getId()));	
			list.setAnswer(a.get(j).getAnswer());	
			//リストへつめる
			returnlist.add(list);
		}
		//削除確認画面へ
		model.addAttribute("rtltForm", returnlist);
		return "delete";
	}
}