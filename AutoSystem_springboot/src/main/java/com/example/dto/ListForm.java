package com.example.dto;

import java.io.Serializable;
import java.util.List;

//Serializableインターフェース
public class  ListForm implements Serializable {
  private static final long serialVersionUID = 1L;

  //privateメンバ変数で定義
  private int id;
  private String question;
  private int answer_id;
  private String answer;
//リスト型用
  private List<ListForm> listForm;

  public ListForm(int id, String question, int answer_id, String answer) {
      this.id = id;
      this.question = question;
      this.answer_id = answer_id;
      this.answer = answer;
  }

  //引数無しのコンストラクタ
  public ListForm() {
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

  public int getAnswer_id() {
      return answer_id;
  }

  public void setAnswer_id(int answer_id) {
      this.answer_id = answer_id;
  }

  public String getAnswer() {
      return answer;
  }

  public void setAnswer(String answer) {
      this.answer = answer;
  }

public List<ListForm> getListForm() {
	return listForm;
}

public void setListForm(List<ListForm> listForm) {
	this.listForm = listForm;
}
  
}