package com.chanyongyang.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chanyongyang.domain.MemberVO;
import com.chanyongyang.domain.NoteVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class NoteMapperTests {
	@Autowired
	private NoteMapper mapper;
	@Autowired
	private MemberMapper memberMapper;
	
//	@Test
//	public void testInsert2() {
//		List<MemberVO> members = memberMapper.getList();
//		int i = 1;
//		for(MemberVO vo : members) {
//			for(MemberVO vo2 : members) {
//				NoteVO noteVO = new NoteVO();
//				noteVO.setSender(vo.getId());
//				noteVO.setReceiver(vo2.getId());
//				noteVO.setMessage("mapper 테스트 발송 :: " + i++);
//				mapper.insert(noteVO);
//				
//			}
//		}
//	}
	
	@Test
	public void testInsert() {
		NoteVO noteVO = new NoteVO();
		noteVO.setSender("id1");
		noteVO.setReceiver("id2");
		noteVO.setMessage("mapper 테스트 발송");
		
		mapper.insert(noteVO);
	}
	
	@Test
	public void testSelectOne() {
		log.info(mapper.selectOne(21L));
	}
	
	@Test
	public void testUpdate() {
		mapper.update(21L);
	}
	
	@Test
	public void testDelete() {
		mapper.delete(21L);
	}
	
	@Test
	public void sendList() {
		mapper.sendList("id3").forEach(log::info);
	}
	
	@Test
	public void receiveList() {
		mapper.receiveList("id3").forEach(log::info);
	}
	
	@Test
	public void receiveUncheckedList() {
		mapper.receiveUncheckedList("id3").forEach(log::info);
	}
	
}
