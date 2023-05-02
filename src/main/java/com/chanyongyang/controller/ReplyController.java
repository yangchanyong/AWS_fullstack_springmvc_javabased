package com.chanyongyang.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanyongyang.domain.ReplyVO;
import com.chanyongyang.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("replies")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
	private ReplyService replyService;
	
	// 목록처리
	// replies/list/{bno}
	// replies/lost/{bno}/{rno}
	
	@GetMapping({"list/{bno}", "list/{bno}/{rno}"})
	public List<ReplyVO> getList(@PathVariable Long bno, @PathVariable(required=false) Optional<Long> rno) {
		log.info(bno);
//		log.info(rno.isPresent() ? rno.get() : 0L);
		log.info(rno.orElse(0L));
//		log.info(rno.ofNullable(0L));
//		if(rno == null) {
//			rno = 0L;
//		}
//		return replyService.getList(bno, rno.orElse(0L));
		// 위 아래는 같은코드임
		return replyService.getList(bno, rno.orElseGet(() -> 0L));
	}
	
	@PostMapping("new")
	@PreAuthorize("isAuthenticated()")
	public Long create(@RequestBody ReplyVO vo) {
		log.info(vo);
		replyService.register(vo);
		return vo.getRno();
	}
	
	// 조회
	@GetMapping("{rno}")
	public ReplyVO get(@PathVariable Long rno) {
		log.info(rno);
		return replyService.get(rno);
	}

	@DeleteMapping("{rno}")
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.replyer")
	public int remove(@PathVariable Long rno, @RequestBody ReplyVO vo) {
		log.info(rno);
		return replyService.remove(rno);
	}

	@PutMapping("{rno}")
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.replyer")
//	@PatchMapping << putMapping과 같음
	// rno는 경로를 알아오기 위한것, vo는 수정대상을 알아오기 위한것
	public int modify(@PathVariable Long rno, @RequestBody ReplyVO vo) {
		log.info(rno);
		log.info(vo);
		return replyService.modify(vo);
	}
}
