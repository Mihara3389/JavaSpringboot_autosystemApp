package com.example.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Common;
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
@RequestMapping(value="/editConfirm",method=RequestMethod.POST)
public class EditComfirmController {

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
	 * 編集画面へ戻る
	 * @param model Model
	 * @return 
	 * @return 編集画面
	 */
	@RequestMapping(params="action=return")
	public String toRedirectList(@ModelAttribute("listForm") ListForm listForm,@ModelAttribute("rtltForm") ArrayList<ReturnlistForm> rtltForm, Model model) 
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
	   * 問題・答え編集
	   * @param registerRequest リクエストデータ
	   * @param model Model
	   * @return 問題一覧画面
	   */
	  @RequestMapping(params="action=update")
	  public String create(@ModelAttribute("rtltForm") ReturnlistForm rtltForm,BindingResult result, Model model) {
	   
		  	//現在時刻を取得
	 		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	 		//取得した問題と答えをリストへ置き換える
	 		int listForm_qId =rtltForm.getId();
	 		String listForm_question =rtltForm.getQuestion();
	 		String str_aId = rtltForm.getAnswer_id();
	 		List<String> form_answerId =Arrays.asList(str_aId.split(",",-1));
	 		String listForm_answer =rtltForm.getAnswer();
	 		List<String> form_answer = Arrays.asList(listForm_answer.split(",",-1));
	 		//更新した問題のidを取得
 			QuestionsEntity q = quesitonsRepository.findByIdEquals(listForm_qId);
	 		//問題の更新
	 		q.setId(listForm_qId);
	 		q.setQuestion(listForm_question);
	 		q.setUpdatedAt(timestamp);	
 			questionsService.updateQuestion(q);
	 		//答えを追加
 			//編集画面のidと一致しないものは削除 			
 			List<AnswersEntity> db_qa = answersRepository.findByQuestionIdEquals(listForm_qId);
 		 	for(int i = 0; i < db_qa.size(); i++) 
 		 	{
 		 		//削除フラグ
 		 		boolean deleteTarget = true;
 		 		int answerId =0;
 		 		for(int j = 0; j < form_answer.size(); j++) 
 		 		{
 		 			//intへ変換
 					answerId = Integer.parseInt(form_answerId.get(j));
 	 				if(db_qa.get(i).getId()== answerId)
 	 				{
 	 					deleteTarget = false;		
 		 			}
 		 		}
 		 		if(deleteTarget == true) {		
					answersService.deleteAnswer(answerId);
 		 		}
 		 	}
 		 	for(int k = 0; k < form_answer.size(); k++)
 			{
 		 		//nullチェック
 				if ((form_answer.get(k)== null))
 				{ 
 					continue; 
 				}
 				if(form_answerId.get(k).isEmpty())
 				{
 					AnswersEntity a = new AnswersEntity();
 					//答えの追加
 					a.setAnswer(form_answer.get(k));
 					a.setQuestionId(listForm_qId);
 					a.setCreatedAt(timestamp);
 					a.setUpdatedAt(timestamp);
 					answersRepository.save(a);	
 				}else
 				{
 					//intへ変換
 					int answerId = Integer.parseInt(form_answerId.get(k));
 					//更新した答えのidを取得
 					AnswersEntity a = answersRepository.findByIdEquals(answerId);
 					a.setId(answerId);
 					//答えの更新
 					a.setAnswer(form_answer.get(k));
 					a.setQuestionId(listForm_qId);
 					a.setUpdatedAt(timestamp);
 					answersService.updateAnswer(a);
 				}
 				
 			}
	 		//質問・答えをDBから取得
	 		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
	 		List<AnswersEntity> answerEntity = answersService.searchAll();
	 		ArrayList<ListForm> listForm = new ArrayList<ListForm>();
			Common com = new Common();
			listForm = com.toCommon(questionsEntity, answerEntity, listForm);  
	 		//問題一覧へ
			model.addAttribute("listForm", listForm);
	 		return "list";
	   }
}