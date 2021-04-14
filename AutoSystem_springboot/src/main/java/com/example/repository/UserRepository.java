package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	//データベースより一致するものを探す
	UserEntity findByUsernameEquals(String username);
	//データベース内にパラメータと同じusernameがあるか探す
	@Query(value = "SELECT u FROM user u where u.username = ?1")
	UserEntity findByOneuser(@Param("username") String username);

}