package com.chanyongyang.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chanyongyang.config.RootConfig;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
@Log4j
public class TimeMapperTest {

	@Autowired
	private TimeMapper mapper;
	
	@Test
	public void testGetTime() {
		log.info(mapper.getTime());
	}
}
