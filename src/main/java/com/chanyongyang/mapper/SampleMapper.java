package com.chanyongyang.mapper;

import org.apache.ibatis.annotations.Insert;

public interface SampleMapper {
	@Insert("insert into tbl_sample1 values (#{data})")
	int insert1(String data);

	@Insert("insert into tbl_sample2 values (#{data})")
	int insert2(String data);
}
