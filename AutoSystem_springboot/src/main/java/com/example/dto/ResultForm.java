package com.example.dto;

import java.sql.Timestamp;

public class ResultForm {
	private int question_count;
	private int correct_count;
	private int result;
	private Timestamp currenTime;
	public int getQuestion_count() {
		return question_count;
	}
	public void setQuestion_count(int question_count) {
		this.question_count = question_count;
	}
	public int getCorrect_count() {
		return correct_count;
	}
	public void setCorrect_count(int correct_count) {
		this.correct_count = correct_count;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Timestamp getCurrenTime() {
		return currenTime;
	}
	public void setCurrenTime(Timestamp currenTime) {
		this.currenTime = currenTime;
	}
}