package com.example.controller;

import java.sql.Timestamp;
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
import com.example.repository.AnswersRepository;
import com.example.repository.QuestionsRepository;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

/**
 * 質問・答え Controller
 */
@Controller
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
  @RequestMapping(value="/register",method=RequestMethod.POST,params="action=registerCheack")
  public String registerCk(@Validated @ModelAttribute ConfrimRequest confrimRequest, BindingResult result, Model model)
  {
	 //答えリストチェック用のフラグ
	 boolean flg = false;
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
       
     }else 
     {
    	 //データベースに一致する質問があるかチェック
    	 QuestionsEntity questionEqual = quesitonsRepository.findByQuestionEquals(confrimRequest.getQuestion());
    	 AnswersEntity answersEqual = answersRepository.findByAnswerEquals(confrimRequest.getAnswer());
 	  
    	 if(questionEqual != null)
    	 {
    		 System.out.println("Question has");
    		 // データベース上にすでに存在する
    		 model.addAttribute("validationError","*入力された質問はすでに登録済です。入力し直してください。");
    		 return "register";
 		  
    	 }else if(answersEqual != null)
    	 {
    		 // データベース上にすでに存在する
    		 model.addAttribute("validationError","*入力された答えはすでに登録済です。入力し直してください。");
    		 return "register";  
    	 }else 
    	 {
    		 System.out.println("Question&Answer not has");
    		 int count =0;
    		 List<ReturnlistForm> listForm = new ArrayList<ReturnlistForm>();
    		 String listForm_answer =confrimRequest.getAnswer();
    		 List<String> form_answer = Arrays.asList(listForm_answer.split(","));
    		 //変数定義
    		 String bf_Answer="";
    		 //答え分ループ
 			 for(int j = 0; j < form_answer.size(); j++)
 			 {
 				//答え未入力チェック
 				if(!form_answer.get(j).isEmpty()){
 					//同じ入力値がないかチェック
 					if(bf_Answer.equals(form_answer.get(j)) )
 					{
 						model.addAttribute("validationError","*同じ値が入力されています。入力し直してください。");			
 						return "register";  
 					}else
 					{
 						if(!form_answer.get(j).isEmpty()) 
 						{
 							flg = true;
 							ReturnlistForm list = new ReturnlistForm();
 							count = count + 1;
 							list.setQuestion(confrimRequest.getQuestion());
 							list.setAnswer_count(count);	
 							list.setAnswer(form_answer.get(j));
 							listForm.add(list);
 							bf_Answer = form_answer.get(j);
 						}
 					}
 				}
 			}
 			 //リストデータが空だったら
 			 if(flg == false) {
 				model.addAttribute("validationError","*答えを入力してください。");			
				return "register";  
 			 }else {
 	 		 //問題・答え確認画面へ
		 	 model.addAttribute("listForm",listForm);
		 	 return "registerConfirm";
 			 }
    	 }
    }
   }
  	/**
	 * 新規登録入力画面へ戻る
	 * @param model Model
	 * @return 
	 * @return 新規登録
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST,params="action=return")
	public String toRedirectList(@ModelAttribute("listForm") ArrayList<ListForm>listForm,Model model) {
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
	  /**
	   * 問題・答え新規登録
	   * @param registerRequest リクエストデータ
	   * @param model Model
	   * @return 新規登録画面
	   */
	  @RequestMapping(value="/registerConfirm",method=RequestMethod.POST,params="action=return")
	  public String toRedirectRegister(@Validated @ModelAttribute ConfrimRequest registerRequest, BindingResult result, Model model) {
	   
	 		//新規登録画面へ
	 		return "register";
	   }
	  /**
	   * 問題・答え新規登録
	   * @param registerRequest リクエストデータ
	   * @param model Model
	   * @return 問題一覧画面
	   */
	  @RequestMapping(value="/registerConfirm",method=RequestMethod.POST,params="action=register")
	  public String create(@ModelAttribute("list") ListForm list,BindingResult result, Model model)
	  {  
	 		//現在時刻を取得
	 		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	 		//取得した問題と答えをリストへ置き換える
	 		String listForm_answer =list.getAnswer();
	 		List<String> form_answer = Arrays.asList(listForm_answer.split(","));
	 		//問題の追加
	 		QuestionsEntity q = new QuestionsEntity();
	 		q.setQuestion(list.getQuestion());
	 		q.setCreatedAt(timestamp);
	 		q.setUpdatedAt(timestamp);
	 		quesitonsRepository.save(q);
	 		//答えを追加
	 		for(int j = 0; j < form_answer.size(); j++)
		 	{
 				//nullチェック
 				if (form_answer.get(j)== null) 
 				{ 
				continue; 
 				}
	 			//追加した問題のidを取得
	 			QuestionsEntity questionEqual = quesitonsRepository.findByQuestionEquals(list.getQuestion());
	 			//答えの追加
	 			AnswersEntity a = new AnswersEntity();
	 			a.setAnswer(form_answer.get(j));
	 			a.setQuestionId(questionEqual.getId());
	 			a.setCreatedAt(timestamp);
	 			a.setUpdatedAt(timestamp);
	 			answersRepository.save(a);	
	 		}
	 		//質問・答えをDBから取得
	 		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
	 		List<AnswersEntity> answerEntity = answersService.searchAll();
	 		ArrayList<ListForm> listForm  = new ArrayList<ListForm>();
			Common com = new Common();
			listForm = com.toCommon(questionsEntity, answerEntity, listForm);  
	 		//問題一覧へ
			model.addAttribute("listForm", listForm);
	 		return "list";
	   }
}