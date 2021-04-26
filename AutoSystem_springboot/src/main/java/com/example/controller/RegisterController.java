package com.example.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.repository.AnswersRepository;
import com.example.repository.QuestionsRepository;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

/**
 * 質問・答え Controller
 */
@Controller
@RequestMapping(value="/register",method=RequestMethod.POST)
public class RegisterController {

  /**
   * 質問・答え
   */
  @Autowired
  private QuestionsRepository quesitonsRepository;
  @Autowired
  private AnswersRepository answersRepository;
  @Autowired
  private QuestionsService questionsService;
  @Autowired
  private AnswersService answersService;

  /**
   * 問題・答え新規登録
   * @param registerRequest リクエストデータ
   * @param model Model
   * @return 問題・答え新規登録確認画面
   */
  @RequestMapping(params="action=registerCheack")
  public String create(@Validated @ModelAttribute ConfrimRequest confrimRequest, @ModelAttribute("listForm") ArrayList<ListForm>listForm, BindingResult result, Model model)
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
       return "register";
       
     } else 
     {
    	 //データベースに一致する質問があるかチェック
    	 QuestionsEntity questionEqual = quesitonsRepository.findByQuestionEquals(confrimRequest.getQuestion());
    	 AnswersEntity answersEqual = answersRepository.findByAnswerEquals(confrimRequest.getAnswer());
 	  
    	 if(questionEqual != null)
    	 {
    		 System.out.println("Question has");
    		 // データベース上にすでに存在する
    		 model.addAttribute("validationError","*入力された質問はすでに登録済です。");
    		 return "register";
 		  
    	 }else if(answersEqual != null)
    	 {
    		 // データベース上にすでに存在する
    		 model.addAttribute("validationError","*入力された答えはすでに登録済です。");
    		 return "register";  
    	 }else 
    	 {
    		 System.out.println("Question&Answer not has");
    		 
    		 ListForm list = new ListForm();
    		 list.setId(Integer.parseInt(confrimRequest.getId()));
    		 list.setQuestion(confrimRequest.getQuestion());
    		 list.setAnswer_id(Integer.parseInt(confrimRequest.getAnswer_id()));
    		 list.setAnswer(confrimRequest.getAnswer());
    		 listForm.add(list);
    		 //問題・答え確認画面へ
		 	 model.addAttribute(listForm);
		 	 return "registerConfirm";
    	 }   	
     }
   }
  	/**
	 * 新規登録入力画面へ戻る
	 * @param model Model
	 * @return 
	 * @return 新規登録
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
	   * 問題・答え新規登録
	   * @param registerRequest リクエストデータ
	   * @param model Model
	   * @return 問題一覧画面
	   */
	  @RequestMapping(value="/registerConfirm",method=RequestMethod.POST,params="action=register")
	  public String create(@Validated @ModelAttribute ConfrimRequest registerRequest, BindingResult result, Model model) {
	   
	 		 //現在時刻を取得
	 		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	 		 //問題の追加
	 		 QuestionsEntity q = new QuestionsEntity();
	 		 q.setQuestion(registerRequest.getQuestion());
	 		 q.setCreatedAt(timestamp);
	 		 q.setUpdatedAt(timestamp);
	 		 quesitonsRepository.save(q);
	 		 //答えの追加
	 		 AnswersEntity a = new AnswersEntity();
	 		 a.setAnswer(registerRequest.getAnswer());
	 		 a.setCreatedAt(timestamp);
	 		 a.setUpdatedAt(timestamp);
	 		 answersRepository.save(a);		  
	 		//質問・答えをDBから取得
	 		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
	 		List<AnswersEntity> answerEntity = answersService.searchAll();
	 		ArrayList<ListForm> listForm  = new ArrayList<ListForm>();
			Common com = new Common();
			listForm = com.toCommon(questionsEntity, answerEntity, listForm);  
	 		//問題一覧へ
	 		return "list";
	   }
}