package com.finalproject.cobit.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.cobit.model.Attend;
import com.finalproject.cobit.model.Stage;
import com.finalproject.cobit.model.StageProgress;
import com.finalproject.cobit.model.User;
import com.finalproject.cobit.repo.AttendRepo;
import com.finalproject.cobit.repo.StageProgressRepo;
import com.finalproject.cobit.repo.StageRepo;
import com.finalproject.cobit.repo.UserRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "승인이 제한되거나 / 승인이 필요하거나 / 키가 없을 때 401 코드가 나타납니다."),
		@ApiResponse(code = 402, message = "IEXCloud의 API를 사용함에 따라 무료이용 요청 한도를 초과하거나 / 무료이용자는 허용하지 않는 요청을 할 경우 402 코드가 나타납니다."),
		@ApiResponse(code = 403, message = "금지되거나 / 유효하지 않은 API Key를 입력하거나 / 잘못된 기호의 종목을 입력하거나 / 비활성화 된 API Key를 가지고 있다면 403 코드가 나타납니다."),
		@ApiResponse(code = 404, message = "US Stock Symbol이 잘못되거나 / 알 수 없는 기호가 입력되었을 때 404 코드가 나타납니다."),
		@ApiResponse(code = 500, message = "이 코드가 나올 시 저에게 문의주세요. 메일 : \"rjsgh1232@naver.com\"") })
@CrossOrigin("*")
@Api(value = "User API")
@RestController
@RequestMapping("/user")
public class UserController {
	/*
	 * < API문서 주소 : http://localhost:8080/stock/swagger-ui.html (<=== CTRL+CLICK!!)
	 * >
	 */
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	AttendRepo attendRepo;

	@Autowired
	StageProgressRepo spRepo;

	@Autowired
	StageRepo stageRepo;

	@ApiOperation(value = "회원정보 가져오기")
	@GetMapping("")
	public User getUser(@RequestParam String email) {
		Optional<User> userOpt = userRepo.getUserByEmail(email);
		return userOpt.get();
	}

	@ApiOperation(value = "회원가입")
	@PostMapping("")
	public User signUp(@RequestBody User user) {

		Optional<User> userOpt = userRepo.getUserByEmail(user.getEmail());
		if (userOpt.isPresent()) {
			return userOpt.get();
		} else {
			user.setStar(0L);
			user.setHint(0L);
			userRepo.save(user);
			Optional<User> userOpt2 = userRepo.getUserByEmail(user.getEmail());
			return userOpt2.get();
		}
	}

	@ApiOperation(value = "회원정보 수정")
	@PutMapping("")
	public ResponseEntity<Boolean> updateUser(@RequestBody User user) {
		Optional<User> userOpt = userRepo.getUserByEmail(user.getEmail());
		userOpt.get().setNickname(user.getNickname());
		userRepo.save(userOpt.get());
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보 탈퇴")
	@DeleteMapping("")
	public ResponseEntity<Boolean> deleteUser(@RequestParam Long id) {
		userRepo.deleteById(id);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@PostMapping("/upload")
	@ApiOperation(value = "사진 업로드")
	public Object upload(@RequestParam MultipartFile image, @RequestParam String email)
			throws IllegalStateException, IOException {
		System.out.println("UPLOAD =======================");
		String filename = image.getOriginalFilename(); // 파일 이름
		System.out.println(filename);
		User user = userRepo.getUserByEmail(email).get(); // 폴더명
//		String filepath = "/image/" + member.getNo() + "/profile";// 폴더 상대 경로
		String filepath = "/dist/img/" + user.getId() + "/profile";// 폴더 상대 경로

		String path = System.getProperty("user.dir") + filepath; // 폴더 상대 경로
		System.out.println(path); // 상대경로
		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성
				System.out.println("폴더가 생성");

			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("폴더가 이미 존재");
		}
		image.transferTo(new File(path + "/" + filename));
		String result = "/img/" + user.getId() + "/profile/" + filename;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "출석현황 가져오기")
	@GetMapping("/attend")
	public List<Attend> getAttend(@RequestParam String email, @RequestParam Long month) {
		List<Attend> list = attendRepo.getUserByEmailAndMonth(email, month);
		return list;
	}

	@ApiOperation(value = "출석체크")
	@PostMapping("/attend")
	public ResponseEntity<Boolean> saveAttend(@RequestBody Attend attend) {
		attendRepo.save(attend);
		Optional<User> userOpt = userRepo.getUserByEmail(attend.getEmail());
		userOpt.get().setHint(userOpt.get().getHint() + 1);
		userRepo.save(userOpt.get());
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@ApiOperation(value = "힌트 구매")
	@PostMapping("/hint")
	public ResponseEntity<Boolean> buyHint(@RequestBody User user) {
		System.out.println(user);
		if (user.getHint() >= 1) {
			user.setHint(user.getHint() - 1);
			userRepo.save(user);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "스테이지 클리어 현황 가져오기")
	@GetMapping("/stage")
	public List<Integer> getStage(@RequestParam Long id) {
		
		List<StageProgress> list = spRepo.getStageProgressByUserId(id);

		int oneCount = 0; // 초급 스테이지 클리어 수
		int twoCount = 0; // 중급 스테이지 클리어 수
		int threeCount = 0; // 고급 스테이지 클리어 수

		for (StageProgress sp : list) {
			Optional<Stage> stageOpt = stageRepo.findById(sp.getStageId());
			if (stageOpt.get().getType() == 1) {
				oneCount++;
			} else if (stageOpt.get().getType() == 2) {
				twoCount++;
			} else if (stageOpt.get().getType() == 3) {
				threeCount++;
			}
		}
		List<Integer> result = new ArrayList<Integer>();
		if (oneCount == 5) {
			result.add(2);
		}
		if (twoCount == 4) {
			result.add(3);
		}

		return result;
	}

}
