package com.tenco.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.blog.model.User;
import com.tenco.blog.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired // DI
	private UserRepository userRepository; 

	@Transactional
	public int createUser(User user) {
		
		try {
			user.setRole("user");
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public User readUser(User user) {
		// 필요한 기능을 JPA가 제공하지 않음 -> 직접 만들기 
		User userEntity = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		return userEntity;
	}
	
}
