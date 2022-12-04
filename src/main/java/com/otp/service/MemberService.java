package com.otp.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.otp.mapper.OtpMapper;


@Service
public class MemberService {
	
	@Autowired
	OtpMapper otpMapper;
	
	public List<Map<String,Object>> selectTestList(){
		return otpMapper.selectTestList();
	} 
	
	public Integer selectTestCount() {
		return otpMapper.selectTestCount();
	}
}
