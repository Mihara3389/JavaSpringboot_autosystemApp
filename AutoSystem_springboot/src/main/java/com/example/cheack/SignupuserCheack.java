package com.example.cheack;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.UserRepository;

public class SignupuserCheack {

		@Autowired
		private UserRepository userRepository;

		public boolean isEmptyByUsername(String username) {
		  
			if(Objects.isNull(userRepository.findByOneuser(username)))
			{  
			  System.out.println("Username not has");
			  return true;
			  
			}else
			{
			  System.out.println("Username has");
			  return false;
			}
		}
	}