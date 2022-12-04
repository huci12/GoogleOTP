package com.otp.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.otp.service.GoogleOTPGenerator;
import com.otp.service.GoogleOTPVertify;
import com.otp.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

	@Autowired
	private GoogleOTPGenerator googleOTPGenerator;
	
	@Autowired
	private GoogleOTPVertify googleOTPVertify;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/")
	public String index(HttpServletRequest requeset) {		
		return "index";
	}
	
	@RequestMapping("/getQrCode")
	@ResponseBody
	public String getQrCode() throws InvalidKeyException, NumberFormatException, NoSuchAlgorithmException {
		return googleOTPGenerator.sendUserOtp();
	}
	
	@RequestMapping("/vertifyCode")
	@ResponseBody
	public boolean vertifyCode(HttpServletRequest request) throws InvalidKeyException, NumberFormatException, NoSuchAlgorithmException {
		
		String code = request.getParameter("code");
		System.out.println(code);
		
		return googleOTPVertify.vertifyCode(code);
	}
	
	@RequestMapping("/selectMembers")
	@ResponseBody
	public ResponseEntity<Object> selectMembers(HttpServletRequest request){
		
		return new ResponseEntity<Object>(memberService.selectTestList() , HttpStatus.OK);
	}
	
	@RequestMapping("/selectCount")
	@ResponseBody
	public ResponseEntity<Object> selectCount(HttpServletRequest request){
		return new ResponseEntity<Object>(memberService.selectTestCount() , HttpStatus.OK);
	}
	
}
