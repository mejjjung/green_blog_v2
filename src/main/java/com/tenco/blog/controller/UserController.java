package com.tenco.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@Autowired
	private HttpSession session;
	
	// 로그인 페이지
	@GetMapping("/loginPage")
	public String loginPage() {
		
		return "/user/login_form";
	}
	
	// 회원가입 페이지
	@GetMapping("/joinPage")
	public String joinPage() {
		
		return "/user/join_form";
	}
	
	// 회원가입 처리
	// 로그아웃
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
}
