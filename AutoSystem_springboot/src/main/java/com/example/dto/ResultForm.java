package com.example.dto;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

	@Getter
	@Setter
	@Data
	public class ResultForm{
		//問題数
	  	private int questionCount;
	  	//正解数
        private int correctCount;
	  	//採点結果
        private int resultTest;
	  	//現在時刻
        private java.sql.Timestamp currenTime;
	        
        ResultForm(int questionCount,int correctCount,int resultTest,Timestamp currenTime){
        	this.questionCount = questionCount;
        	this.correctCount  = correctCount;
        	this.resultTest = resultTest;
        	this.currenTime = currenTime;
        }
        
        //引数無しコンストラクタ
        ResultForm(){
        	
        }
        
        public int getQuestioncount() {
    		return questionCount;
    	}
    	public void setQuestioncount(int questionCount) {
    		this.questionCount = questionCount;
    	}
    	public int getCorrectcount() {
    		return correctCount;
    	}
    	public void setCorrectcount(int correctCount) {
    		this.correctCount = correctCount;
    	}
    	public int getResulttest() {
    		return resultTest;
    	}
    	public void setResulttest(int resultTest) {
    		this.resultTest = resultTest;
    	}
       	public Timestamp getCurrentime() {
    		return currenTime;
    	}
    	public void setCurrentime(Timestamp currenTime) {
    		this.currenTime = currenTime;
    	}
    
	    }