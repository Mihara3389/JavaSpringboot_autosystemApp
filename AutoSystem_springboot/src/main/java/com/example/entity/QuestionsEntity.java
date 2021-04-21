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
	 * 質問 Entity
	 */
	@Entity
	@Data
	@Table(name="questions")
	public class QuestionsEntity implements Serializable {

	  /**
	   * ID
	   */
	  @Id
	  @Column(name="id")
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private int id;

	  /**
	   * 質問
	  */
	  @Column(name="question")
	  private String question;

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

	  public int getId() {
			return this.id;
		}
	  public void setId(int id) {
	        this.id = id;
	    }
	  
	  public void setQuestion(String question) {
	        this.question = question;
	    }
		
	  public String getQuestion() {
			return this.question;
		}
	  
	  public void setCreatedAt(java.sql.Timestamp created_at) {
		  this.created_at = created_at;	  
	  }

	  public Timestamp getCreatedAt() {
		  return this.created_at;	
	  }		
	  
	  public void setUpdatedAt(java.sql.Timestamp updated_at) {
		  this.updated_at = updated_at;	  
	  }

	  public Timestamp getUpdatedAt() {
		  return this.updated_at;	
	  }	
	  
	}