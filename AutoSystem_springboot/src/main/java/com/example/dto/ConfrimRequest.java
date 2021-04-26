package com.example.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 問題・答え リクエストデータ
 */
@Data
public class ConfrimRequest implements Serializable {
	
  /**
   * 問題id
   */
  private String id;

  /**
   * 問題
   */
  @NotEmpty(message = "問題を入力してください")
  @Size(max = 500, message = "問題は500桁以内で入力してください")
  private String question;
  
  /**
   * 答えid
   */
  private String answer_id;
  
  /**
   * 答え
   */
  @NotEmpty(message = "答えを入力してください")
  @Size(max = 200, message = "答えは200桁以内で入力してください")
  private String answer;
}