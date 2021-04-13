package com.example.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 新規登録ユーザ Entity
 */
@Entity
@Data
@Table(name="user")
public class SignupEntity implements Serializable {

/**
 * ID
 */
@Id
@Column(name="id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
 /**
  * 名前
  */
@Column(name="username")
private String username;
/**
 * パスワード
 */
@Column(name="password")
private String password;
/**
 * 登録日時
 */
@Column(name="created_at")
private java.sql.Timestamp created_at;
/**
 * 更新日時
 */
@Column(name="updated_at")
private java.sql.Timestamp updated_at;

public void setUsername(String username) {
    this.username = username;
}

public void setPassword(String password) {
    this.password = password;
}

public void setCreatedAt(java.sql.Timestamp created_at) {
    this.created_at = created_at;
}

public void setUpdatedAt(java.sql.Timestamp updated_at) {
    this.updated_at = updated_at;
}

public Timestamp getUpdateddAt() {
	return this.created_at;
}
}