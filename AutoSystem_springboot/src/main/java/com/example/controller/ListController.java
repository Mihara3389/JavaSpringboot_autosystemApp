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
import com.example.dto.DeleteForm;
import com.example.dto.EditForm;
import com.example.dto.ListForm;
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
	 * 新規登録画面を表示
	 * @param model Model
	 * @return 新規登録画面
	 */
	@RequestMapping(params="action=register")
	public String postRegister(Model model) 
	{
		model.addAttribute("confirmRequest}", new ConfrimRequest());
		return "register";
	}
	/**
	 * 編集データを表示
	 * @param model Model
	 * @return 編集
	 */
	@RequestMapping(params="action=edit")
	public String postEdit(@ModelAttribute("listForm") ListForm listForm,@ModelAttribute("editForm") ArrayList<EditForm> editForm, Model model) 
	{
		//問題idを取得
		int questionId =listForm.getId();
		//取得した問題idを使用して問題と答えを取得する
		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
		List<AnswersEntity> answerEntity = answersService.searchAll();
		//変数定義
		int db_questionsId =0;
		int db_answersId =0;
		int db_answers_QuestionId=0;
		int count =0;
		//questionsループ
		for(int i = 0; i < questionsEntity.size(); i++) 
		{
			if(questionsEntity.isEmpty()) 
			{
				continue;
			}else
			{
				//問題を取得
				db_questionsId = questionsEntity.get(i).getId();
				if(db_questionsId==questionId) 
				{
				//answersループ
					for(int j = 0; j < answerEntity.size(); j++) 
					{
						//nullチェック
						AnswersEntity answer = answerEntity.get(j); 
						if (answer == null) 
						{ 
							continue; 
						}
						//照合
						db_answersId = answerEntity.get(j).getId();
						db_answers_QuestionId = answerEntity.get(j).getQuestion_id();
						if(db_questionsId == db_answers_QuestionId) 
						{
							//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
							EditForm editlist = new EditForm();
							//同じ問題IDの答え数
							count = count+1;
							//値をつめる
							editlist.setId(db_questionsId);
							editlist.setQuestion(questionsEntity.get(i).getQuestion());
							editlist.setAnswer_count(count);
							editlist.setAnswer_id(Integer.toString(db_answersId));	
							editlist.setAnswer(answerEntity.get(j).getAnswer());	
							//リストへつめる
							editForm.add(editlist);
						}
					}
				}
			}			
		}
			model.addAttribute("editForm", editForm);
			return "edit";
	}
	/**
	 * 削除データを表示
	 * @param model Model
	 * @return 削除
	 */
	@RequestMapping(params="action=delete")
	public String postDelete(@ModelAttribute("listForm") ListForm listForm,@ModelAttribute("deleteForm") ArrayList<DeleteForm> deleteForm,Model model) 
	{
		//問題idを取得
		int questionId =listForm.getId();
		//取得した問題idを使用して問題と答えを取得する
		List<QuestionsEntity> questionsEntity = questionsService.searchAll();
		List<AnswersEntity> answerEntity = answersService.searchAll();
		//変数定義
		int db_questionsId =0;
		int db_answersId =0;
		int db_answers_QuestionId=0;
		int count =0;
		//questionsループ
		for(int i = 0; i < questionsEntity.size(); i++)
		{
			if(questionsEntity.isEmpty()) 
			{
				continue;
			}else
			{
				//問題を取得
				db_questionsId = questionsEntity.get(i).getId();
				if(db_questionsId==questionId)
				{
				//answersループ
					for(int j = 0; j < answerEntity.size(); j++) 
					{
						//nullチェック
						AnswersEntity answer = answerEntity.get(j); 
						if (answer == null) 
						{ 
							continue; 
						}
						//照合
						db_answersId = answerEntity.get(j).getId();
						db_answers_QuestionId = answerEntity.get(j).getQuestion_id();
						if(db_questionsId == db_answers_QuestionId) 
						{
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
}