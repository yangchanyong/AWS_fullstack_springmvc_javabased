package com.chanyongyang.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	@Select("select sysdate from dual")
	public String getTime();
	
	public String getTime2();
	
	public List<Map<String, Object>> memberList();
}
