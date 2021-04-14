package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.entity.UserEntity;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	//データベースより一致するものを探す
	UserEntity findByUsernameEquals(String username);
}