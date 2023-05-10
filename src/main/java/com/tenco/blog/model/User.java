package com.tenco.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data // 주의 (object Mapper) 파싱 처리 할 때 setter 반드시 있어야 한다. 
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, length = 30)
	private String username; 
	
	@Column(nullable = false, length = 100)
	private String password; 
	
	@Column(nullable = false, length = 50)
	private String email; 
	
	@ColumnDefault("'user'") // 문자열 타입 이라고 명시는 ''(홑따옴표) 
	private String role; // user, admin, manager 
	
	@CreationTimestamp // 자동 시간 입력 == now() 
	private Timestamp createdDate;
	
}