package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Common;
import com.example.dto.ConfrimRequest;
import com.example.dto.ListForm;
import com.example.dto.ReturnlistForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

	/**
	 * 編集データ Controller
	 */
	@Controller
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public class EditController{
	/**
	 * 編集データ Service
	 */
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private AnswersService answersService;
	
	  /**
	   * 問題・答え編集
	   * @param registerRequest リクエストデータ
	   * @param model Model
	   * @return 問題・答え編集確認画面
	   */

	  @RequestMapping(params="action=editCheack")
	  public String editCk(@Validated @ModelAttribute ConfrimRequest confrimRequest, BindingResult result, Model model)
	  {
	     //入力値のチェック
	     if (result.hasErrors()) 
	     {
	       List<String> errorList = new ArrayList<String>();
	       for (ObjectError error : result.getAllErrors()) 
	       {
	         errorList.add(error.getDefaultMessage());
	       }
	       model.addAttribute("validationError", errorList);
	       return "edit";
	       
	     } else 
	     {
			 List<ReturnlistForm> rtltForm = new ArrayList<ReturnlistForm>();
			 List<String> form_answerId = Arrays.asList(confrimRequest.getAnswer_id().split(",",-1));
			 List<String> form_answer = Arrays.asList(confrimRequest.getAnswer().split(",",-1));
			 //変数定義
	    	 int count =0;
			 String bf_Answer="";
			 String empty_AnswerId="";
			 //答え分ループ
			 for(int j = 0; j < form_answer.size(); j++)
			 {
				//nullチェック
				if (form_answer.get(j)== null)
				{ 
					continue; 
				}
				
				ReturnlistForm list = new ReturnlistForm();
				count = count + 1;
				//同じ入力値がないかチェック
				if(bf_Answer.equals(form_answer.get(j)) )
				{
					model.addAttribute("validationError","*同じ値が入力されています。入力し直してください。");			
					return "register";  
				}else 
				{	
					if(!form_answer.get(j).isEmpty()) 
					{
						if(form_answerId.get(j).isEmpty())
						{
							list.setAnswer_id(empty_AnswerId);	
						}else {
							list.setAnswer_id(form_answerId.get(j));	
						}
						list.setId(Integer.parseInt(confrimRequest.getId()));
						list.setQuestion(confrimRequest.getQuestion());
						list.setAnswer_count(count);
						list.setAnswer(form_answer.get(j));
						rtltForm.add(list);		
						bf_Answer = form_answer.get(j);
					}
				 }
	     	}
	  		 //問題・答え確認画面へ
		 	 model.addAttribute("rtltForm",rtltForm);
		 	 return "editConfirm";
	     }   	
	   }
	/**
	 * 問題一覧画面へ戻る
	 * @param model Model
	 * @return 
	 * @return 問題一覧
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
	}