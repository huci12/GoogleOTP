package com.otp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OtpMapper {
	public List<Map<String, Object>> selectTestList();
	public Integer selectTestCount();
}
