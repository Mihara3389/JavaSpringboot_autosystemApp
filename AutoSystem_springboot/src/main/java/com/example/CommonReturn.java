package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.dto.ListForm;
import com.example.dto.ReturnlistForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;

public class CommonReturn
{
	public ArrayList<ReturnlistForm> toCommon(@ModelAttribute("listForm") ListForm listForm,List<QuestionsEntity> questionsEntity,List<AnswersEntity> answerEntity, ArrayList<ReturnlistForm> returnlist)
	{
		//問題idを取得
		int questionId =listForm.getId();
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
						db_answersId = answer.getId();
						db_answers_QuestionId = answer.getQuestionId();
						if(db_questionsId == db_answers_QuestionId) 
						{
							//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
							ReturnlistForm list = new ReturnlistForm();
							//同じ問題IDの答え数
							count = count+1;
							//値をつめる
							list.setId(db_questionsId);
							list.setQuestion(questionsEntity.get(i).getQuestion());
							list.setAnswer_count(count);
							list.setAnswer_id(Integer.toString(db_answersId));	
							list.setAnswer(answerEntity.get(j).getAnswer());	
							//リストへつめる
							returnlist.add(list);
						}
					}
					//質問だけしかない場合
					if(count==0) 
					{
						//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
						ReturnlistForm list = new ReturnlistForm();
						//値をつめる
						list.setId(db_questionsId);
						list.setQuestion(questionsEntity.get(i).getQuestion());
						list.setAnswer_id(Integer.toString(0));	
						list.setAnswer(null);
						//リストへつめる
						returnlist.add(list);
					}
				}
			}
		}
	return returnlist;
	}
}