package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import com.mysql.cj.exceptions.PasswordExpiredException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	UserEntity user = userRepository.findByUsernameEquals(username);
    	return user;
    }
    public UserDetails loadUserByPassword(String password) throws PasswordExpiredException {
    	UserEntity pass = userRepository.findByPasswordEquals(password);
    	return pass;
    }

	
}