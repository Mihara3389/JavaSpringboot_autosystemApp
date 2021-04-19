package com.example.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.TestForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;
import com.example.service.AnswersService;
import com.example.service.QuestionsService;

	/**
	 * テスト Controller
	 */
	@Controller
	public class TestController {
	/**
	 * テスト Service
	 */
	@Autowired
	private QuestionsService questionsService;
	private AnswersService answersService;
	
	/**
	 * テストを表示
	 * @param model Model
	 * @return テスト
	 */
	@RequestMapping(value="/top", method=RequestMethod.POST,params="action=test")
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
	 * テスト結果を表示
	 * @param model Model
	 * @return テスト結果
	 */
	//テストフォームに出力されている問題番号・答えを取得
	@RequestMapping(value="/test", method=RequestMethod.POST)
	public String executeLogin(@ModelAttribute("testForm") TestForm testForm, Model model) {
		//現在時刻取得
		Timestamp currenTime = new Timestamp(System.currentTimeMillis());
		//取得した問題をリストへ置き換える
		List<TestForm> formTest = Arrays.asList(testForm);
		//DB内の答えを取得
		List<AnswersEntity> answers = answersService.searchAll();
		//カウンターの宣言と初期化
		int question_count=0;
		int correct_count=0;
		//採点チェック
		//問題数分ループ
		for(int i = 0; i < formTest.size(); i++){
			int formId = formTest.get(i).getQuestions_id();
			String formAnswer = formTest.get(i).getAnswer();
			question_count = question_count + 1;
			//答え分ループ
			for(int j = 0; j < answers.size(); j++){
				//問題のidと答えの問題idが一致したら
				int answerId = answers.get(j).getQuestionId();
				String answerAnswer = answers.get(j).getAnswer();
				if(formId == answerId){
					//答えの内容が一致したら
					if(formAnswer.equals(answerAnswer)) {
						correct_count = correct_count + 1;
					}
				}
			}
		}
		//採点を結果（小数点以下四捨五入）
		double result = correct_count/question_count;
		Math.round(result);
		//取得したデータをリストへ格納する
		List<Object> resultList =  new ArrayList<Object>();
		resultList.add(question_count);
		resultList.add(correct_count);
		resultList.add(result);
		resultList.add(currenTime);
		//テスト結果画面へ
		model.addAttribute("resultList", resultList);
		return "test_cheack";
	}
}