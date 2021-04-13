package com.example.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class SignupRequest implements Serializable {

  /**
   * 名前
   */
  @NotEmpty(message = "名前を入力してください")
  @Size(max = 63, message = "名前は63桁以内で入力してください")
  private String username;

  /**
   * パスワード
   */
  @NotEmpty(message = "パスワードを入力してください")
  @Size(max = 255, message = "パスワードは255桁以内で入力してください")
  @Pattern(regexp = "^[a-zA-Z0-9]+$")
  private String password;
}