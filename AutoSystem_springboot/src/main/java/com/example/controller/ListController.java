package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.CommonReturn;
import com.example.dto.ConfrimRequest;
import com.example.dto.ListForm;
import com.example.dto.ReturnlistForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

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
	private QuestionsService questionsService;
	@Autowired
	private AnswersService answersService;
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
	public String postEdit(@ModelAttribute("listForm") ListForm listForm,@ModelAttribute("rtltForm") ArrayList<ReturnlistForm> rtltForm, Model model) 
	{
		//取得した問題idを使用して問題と答えを取得する
		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
		List<AnswersEntity> answerEntity = answersService.searchAll();
		//問題を取得
		CommonReturn crt = new CommonReturn();
		rtltForm = crt.toCommon(listForm, questionsEntity, answerEntity, rtltForm);
		//編集画面へ
		model.addAttribute("rtltForm", rtltForm);
		return "edit";
	}
	/**
	 * 削除データを表示
	 * @param model Model
	 * @return 削除
	 */
	@RequestMapping(params="action=delete")
	public String postDelete(@ModelAttribute("listForm") ListForm listForm,@ModelAttribute("rtltForm") ArrayList<ReturnlistForm> rtltForm,Model model) 
	{
		//取得した問題idを使用して問題と答えを取得する
				List<QuestionsEntity> questionsEntity = questionsService.searchAll();
				List<AnswersEntity> answerEntity = answersService.searchAll();
				//問題を取得
				CommonReturn crt = new CommonReturn();
				rtltForm = crt.toCommon(listForm, questionsEntity, answerEntity, rtltForm);
				//編集画面へ
				model.addAttribute("rtltForm", rtltForm);
				return "delete";
	}
}