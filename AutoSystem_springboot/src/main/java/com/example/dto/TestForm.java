package com.example.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TestForm  implements Serializable{
	private String questions_id;
	private String questions_question;
	private String answer;
	
	private List<TestForm> testForm;
	
	public String getQuestions_id() {
		return questions_id;
	}
	public void setQuestions_id(String questions_id) {
		this.questions_id = questions_id;
	}
	public String getQuestions_question() {
		return questions_question;
	}
	public void setQuestions_question(String questions_question) {
		this.questions_question = questions_question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<TestForm> getTestForm() {
		return testForm;
	}
	public void setTestForm(List<TestForm> testForm) {
		this.testForm = testForm;
	}


}