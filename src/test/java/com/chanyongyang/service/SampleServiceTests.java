package com.chanyongyang.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleServiceTests {
	
	@Autowired
	private SampleService service;
	
	@Test
	public void testExist() {
		assertNotNull(service);
	}

	@Test
	public void testAddData() throws Exception {
		String data = "이후 개발 예정지 인근 아파트 매매 가격이 1억원 넘게 오르는가 하면, 정부 발표 전 매도 계약서를 썼던 집주인들이 위약금을 감수하며 계약을 취소하는 사례도 속출하고 있습니다.";
		byte[] bs = data.getBytes("utf-8");
		log.info(bs.length);
		byte[] bs2 = new byte[50];
		System.arraycopy(bs, 0, bs2, 0, 50);
		log.info(bs2.length);
		String str = new String(bs2, "utf-8");
		log.info(str);
//		data = "abcd";
//		log.info(data.length());
		
		service.addData(data);
	}
}
