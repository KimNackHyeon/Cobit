package com.finalproject.cobit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.cobit.model.Stage;
import com.finalproject.cobit.model.StageProgress;
import com.finalproject.cobit.repo.StageProgressRepo;
import com.finalproject.cobit.repo.StageRepo;

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
@Api(value = "Stage API")
@RestController
@RequestMapping("/stage")
public class StageController {
	/*
	 * < API문서 주소 : http://localhost:8080/stock/swagger-ui.html (<=== CTRL+CLICK!!)
	 * >
	 */

	@Autowired
	StageRepo stageRepo;
	
	@Autowired
	StageProgressRepo spRepo;

	@ApiOperation(value = "모든 스테이지 정보 가져오기")
	@GetMapping("")
	public List<Stage> getStage() {
		List<Stage> list = stageRepo.findAll();
		return list;
	}
	
	@ApiOperation(value = "나의 스테이지 정보 가져오기")
	@GetMapping("/user")
	public List<StageProgress> getMyStage(@RequestParam Long id) {
		List<StageProgress> list = spRepo.getStageProgressByUserId(id);
		return list;
	}

}
