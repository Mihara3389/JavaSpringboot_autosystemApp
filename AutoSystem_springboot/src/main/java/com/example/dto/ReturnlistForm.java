package com.example.dto;

import java.io.Serializable;
import java.util.List;

//Serializableインターフェース
public class  ReturnlistForm implements Serializable {
  private static final long serialVersionUID = 1L;

  //privateメンバ変数で定義
  private int id;
  private String question;
  private String answer_id;
  private int answer_count;
  private String answer;
//リスト型用
  private List<ReturnlistForm> listForm;

  public ReturnlistForm(int id, String question, String answer_id,int answer_count, String answer) {
      this.id = id;
      this.question = question;
      this.answer_count =answer_count;
      this.answer_id = answer_id;
      this.answer = answer;
  }

  //引数無しのコンストラクタ
  public ReturnlistForm() {
  }

  //getter(参照するためのアクセッサー）/setter(設定するためのアクセッサ-）
  public int getId() {
      return id;
  }

  public void setId(int id) {
      this.id = id;
  }

  public String getQuestion() {
      return question;
  }

  public void setQuestion(String question) {
      this.question = question;
  }

  public String getAnswer_id() {
      return answer_id;
  }

  public void setAnswer_id(String answer_id) {
      this.answer_id = answer_id;
  }
  
  public int getAnswer_count() {
      return answer_count;
  }

  public void setAnswer_count(int answer_count) {
      this.answer_count = answer_count;
  }
  public String getAnswer() {
      return answer;
  }

  public void setAnswer(String answer) {
      this.answer = answer;
  }

public List<ReturnlistForm> getListForm() {
	return listForm;
}

public void setListForm(List<ReturnlistForm> listForm) {
	this.listForm = listForm;
}
  
}