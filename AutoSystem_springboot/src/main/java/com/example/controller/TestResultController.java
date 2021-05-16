package com.example.controller;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.ResultForm;
import com.example.dto.TestForm;
import com.example.entity.AnswersEntity;
import com.example.service.AnswersService;

@Controller
@RequestMapping(value="/test", method=RequestMethod.POST)
public class TestResultController {
	/**
	 * TestResult Service
	 */
	@Autowired
	private AnswersService answersService;
	/**
	 * 採点結果を表示
	 * @param model Model
	 * @return テスト
	 */
	@RequestMapping(params="action=Cheack")
	public String postTestCheack(@ModelAttribute("testForm") TestForm testForm,@ModelAttribute("resultForm") ResultForm resultForm,Model model) {
		//カウンターの宣言と初期化
		double question_count=0;
		double correct_count=0;
		//現在時刻取得
		Timestamp currenTime = new Timestamp(System.currentTimeMillis());
		//取得した問題をリストへ置き換える
		List<String> form_questions_id = Arrays.asList(testForm.getQuestions_id().split(","));
		List<String> form_answer = Arrays.asList(testForm.getAnswer().split(","));
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