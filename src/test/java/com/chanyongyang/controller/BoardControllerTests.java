package com.chanyongyang.controller;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@WebAppConfiguration
@Log4j
public class BoardControllerTests {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before // junit의 Before
	public void init() { // mockMvc의 초기화 작업
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	// ==================== 테스트 준비작업 끝 ==================
	
	@Test
	public void testExist() {
		log.info(context);
		log.info(mockMvc);
	}
	
	@Test
	public void testList() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.get("/board/list").param("amount", "20").param("pageNum", "5");
		ModelMap map = mockMvc.perform(builder).andReturn().getModelAndView().getModelMap();
//		log.info(map.get("List"));
		
		List<?> list = (List<?>) map.get("list");
		list.forEach(log::info);
	}

	@Test
	public void testGet() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders
				.get("/board/get")
				.param("bno", "1");
		ModelMap map = mockMvc.perform(builder).andReturn().getModelAndView().getModelMap();
		
		log.info(map.get("board"));
	}
	
	@Test
	public void testRegister() throws Exception {
		RequestBuilder builder = 
				MockMvcRequestBuilders
				.post("/board/register")
				.param("title", "컨트롤러 테스트 제목")
				.param("content", "컨트롤러 테스트 내용")
				.param("writer", "컨트롤러 테스터");
		String result = mockMvc.perform(builder).andReturn().getModelAndView().getViewName();
		log.info(result);
	}
	
	@Test
	public void testModify() throws Exception {
		RequestBuilder builder = 
				MockMvcRequestBuilders
				.post("/board/modify")
				.param("bno", "7")
				.param("title", "컨트롤러 테스트 제목")
				.param("content", "컨트롤러 테스트 내용")
				.param("writer", "컨트롤러 테스터");
		String result = mockMvc.perform(builder).andReturn().getModelAndView().getViewName();
		log.info(result);
	}
	
	@Test
	public void testRemove() throws Exception {
		RequestBuilder builder = 
				MockMvcRequestBuilders
				.post("/board/remove")
				.param("bno", "8");
		String result = mockMvc.perform(builder).andReturn().getModelAndView().getViewName();
		log.info(result);
	}
}
