package com.chanyongyang.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chanyongyang.mapper.SampleMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
@Transactional // 트랜잭션에 사용되는 어노테이션 메서드 혹은 클래스에 붙이면 된다.
public class SampleService {
	private SampleMapper mapper;
	
	public void addData(String data) {
		log.info("inser1()");
		mapper.insert1(data);
		log.info("inser2()");
		mapper.insert2(data);
		log.info("end");
	}
	public void addData2(String data) {
		log.info("inser1()");
		mapper.insert1(data);
		log.info("inser2()");
		mapper.insert2(data);
		log.info("inser2()");
		mapper.insert2(data);
		log.info("inser2()");
		mapper.insert2(data);
		log.info("end");
	}
}
