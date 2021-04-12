package com.example.entity;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

//ユーザー情報Entity

@Entity
@Getter
@Setter
@Table(name="user")
public class UserEntity implements UserDetails{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;
	
	@Column(name="created_at")
	private java.sql.Timestamp created_at;

	@Column(name="updated_at")
	private java.sql.Timestamp updated_at;

	@Column(name="deleteflag")
	private byte deleteflag;
	
	@Column(name="deleted_at")
	private java.sql.Timestamp deleted_at;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return null;
	}

	public void setUsername(String username) {
        this.username = username;
    }
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(String password) {
        this.password = password;
    }
	@Override
	public String getPassword() {
		return this.password;
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

	public Timestamp getUpdateddAt() {
		return this.created_at;
	}
	
	public void setDeleteFlag(Byte deleteflag) {
        this.deleteflag = deleteflag;
    }
	
	public byte getDeleteFlag() {
		return this.deleteflag;
	}
	
	public void setDeletedAt(java.sql.Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }
	
	public Timestamp getDeletedAt() {
		return this.deleted_at;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}