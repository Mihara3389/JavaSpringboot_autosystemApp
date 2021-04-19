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
	 * ユーザー情報 Entity
	 */
	@Entity
	@Data
	@Table(name="histories")
	public class HistoryEntity implements Serializable {

	  /**
	   * ID
	   */
	  @Id
	  @Column(name="id")
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private int id;

	  /**
	   * user_id
	  */
	  @Column(name="user_id")
	  private int user_id;

	  /**
	   * 得点
	   */
	  @Column(name="point")
	  private int point;

	  /**
	   * 登録日時
	   */
	  @Column(name="created_at")
	  private java.sql.Timestamp created_at;

	  public int getId() {
			return this.id;
		}
	  public void setId(int id) {
	        this.id = id;
	    }
	  
	  public void setUserid(int user_id) {
	        this.user_id = user_id;
	    }
		
	  public int getUserid() {
			return this.user_id;
		}
	  
	  public void setPoint(int point) {
	        this.point = point;
	    }
		
	  public int getPoint() {
			return this.point;
		}

	  public void setCreatedAt(java.sql.Timestamp created_at) {
		  this.created_at = created_at;	  
	  }

	  public Timestamp getCreatedAt() {
		  return this.created_at;	
	  }		
	  
	}