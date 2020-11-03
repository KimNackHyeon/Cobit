package com.finalproject.cobit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.cobit.model.Product;
import com.finalproject.cobit.model.Purchase;
import com.finalproject.cobit.model.User;
import com.finalproject.cobit.repo.ProductRepo;
import com.finalproject.cobit.repo.PurchaseRepo;
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
@Api(value = "Product API")
@RestController
@RequestMapping("/product")
public class ProductController {
	/*
	 * < API문서 주소 : http://localhost:8080/stock/swagger-ui.html (<=== CTRL+CLICK!!)
	 * >
	 */

	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	PurchaseRepo purchaseRepo;
	
	@Autowired
	UserRepo userRepo;

	@ApiOperation(value = "상품 정보 가져오기")
	@GetMapping("")
	public Product getProduct(@RequestParam Long id) {
		Optional<Product> productOpt = productRepo.findById(id);
		return productOpt.get();
	}
	
	@ApiOperation(value = "상품 정보 구매하기")
	@PostMapping("")
	public ResponseEntity<Boolean> saveProduct(@RequestBody Purchase p) {
		purchaseRepo.save(p);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@ApiOperation(value = "내 아이템 정보 가져오기")
	@GetMapping("/user")
	public List<Product> getPurchase(@RequestParam String email) {
		
		Optional<User> userOpt = userRepo.getUserByEmail(email);
		
		// 회원 구매 목록 가져오기
		List<Purchase> purList = purchaseRepo.getPurchaseByUserId(userOpt.get().getId());
		
		List<Product> proList = new ArrayList<Product>();
		for (Purchase p : purList) {
			Optional<Product> proOpt = productRepo.findById(p.getProductId());
			proList.add(proOpt.get());
		}
		
		return proList;
	}

}
