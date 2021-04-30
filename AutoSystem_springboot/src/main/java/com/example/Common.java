package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.dto.ListForm;
import com.example.entity.AnswersEntity;
import com.example.entity.QuestionsEntity;

public class Common
{
	public ArrayList<ListForm> toCommon(List<QuestionsEntity> questionsEntity,List<AnswersEntity> answerEntity, ArrayList<ListForm> returnlist)
	{
		//変数定義
		int db_questionsId =0;
		int db_answersId =0;
		int count =0;
		//questionsループ
		for(int i = 0; i < questionsEntity.size(); i++) 
		{
			//カウント初期化
			count = 0;
			//問題を取得
			db_questionsId = questionsEntity.get(i).getId();
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
				db_answersId = answerEntity.get(j).getQuestionId();
				//質問のidと答えの問題idが一致する場合
				if(db_questionsId == db_answersId)
				{
					//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
					ListForm list = new ListForm();
					//同じ問題IDの答え数
					count = count+1;
					//値をつめる
					list.setId(db_questionsId);
					list.setQuestion(questionsEntity.get(i).getQuestion());
					list.setAnswer_id(count);	
					list.setAnswer(answerEntity.get(j).getAnswer());	
					//リストへつめる
					returnlist.add(list);
				}
			}
			//質問だけしかない場合
			if(count==0) 
			{
				//箱を新しくする(じゃないと同じ箱を使いまわしリスト状態にならない）
				ListForm list = new ListForm();
				//値をつめる
				list.setId(db_questionsId);
				list.setQuestion(questionsEntity.get(i).getQuestion());
				list.setAnswer_id(0);	
				list.setAnswer(null);
				//リストへつめる
				returnlist.add(list);
			}
		}
		return returnlist;
	}
}
		