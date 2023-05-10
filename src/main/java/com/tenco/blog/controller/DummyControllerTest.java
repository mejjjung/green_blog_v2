package com.tenco.blog.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenco.blog.model.User;
import com.tenco.blog.repository.UserRepository;

@RestController // Ioc 처리
public class DummyControllerTest {

	// DI
	// @Autowired
	private UserRepository userRepository;

	public DummyControllerTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// MIME TYPE - application/json
	// 회원 등록 - 샘플
	@PostMapping("/dummy/insert-user")
	public String insertUser(@RequestBody User user) {
		// 유효성 검사
		user.setRole("user");
		userRepository.save(user);
		System.out.println(user.toString());

		return "회원가입에 성공";
	}

	// localhost:8080/dummy/user/1
	@GetMapping("/dummy/user/{id}")
	public User getUser(@PathVariable Integer id) {
		// optional - user 있을 수도 있고 null일 수도 있다.

		// 인증 검사, 유효성 검사
		// Optional<User> userOp = userRepository.findById(id);

		// 1 null일 일이 없을 때 사용
		// User user = userRepository.findById(id).get();

		// 2 데이터가 있으면 그대로 반환 없으면 직접 정의한 객체를 반환 시킬 수 있다.
//		User user = userRepository.findById(id).orElseGet( () -> {
//			return new User();
//		});

		// 3 있으면 반환 없으면 예외 던진다.
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없네요?");
		});

		System.out.println(user);

		// 1. get() : null일 일이 없을 때 사용
		// 2. orElseGet : 데이터가 있으면 그대로 반환 없으면 직접 정의한 객체를 반환 시킬 수 있다.
		// 3. orElseThrow : 있으면 반환 없으면 예외 던진다.
		return user;
	}
	
	// 페이지 content만 가지고 오기 
	@GetMapping("/dummy/users")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id",
			direction = Direction.DESC) Pageable pageble) {
		// List<User> users = userRepository.findAll();
		Page<User> pageUser = userRepository.findAll(pageble);
		return pageUser.getContent();
	}
	
	// JSON 던질 예정 
	// Update를 할 때 
	// 1 기존 로직 처리
	// 2 dirty checking 사용
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable Integer id, @RequestBody User reqUser) {
		
		// 인증 검사, 유효성검사 
		// 존재 여부 확인
		// 영속화된 데이터 
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
		});
		
		// 클라이언트가 던진 데이터 
		// reqUser
		userEntity.setEmail(reqUser.getEmail());
		userEntity.setPassword(reqUser.getPassword());
		// 저장 처리
		// userRepository.save(userEntity);
		// dirty checking 사용
		// save를 호출하지 않았는데 변경 처리 되었다. 
		// 트랜젝션 내에서 트랜젝션이 끝나기 전에 영속성 컨텍스에 
		// 1차 캐쉬에 들어가 있는 데이터 상태를 변경 감지한다.
		return userEntity;
	}
	
	
}
