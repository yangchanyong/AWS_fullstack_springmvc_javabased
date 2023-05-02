package com.chanyongyang.service;

import java.util.List;

import com.chanyongyang.domain.AttachFileDTO;
import com.chanyongyang.domain.BoardVO;
import com.chanyongyang.domain.Criteria;
// dao가 아닌 mapper를 가지게됨
public interface BoardService {
	
	void register(BoardVO vo);
	
	BoardVO get(Long bno);
	
	boolean modify(BoardVO vo);
	
	boolean remove(Long bno);
	
//	List<BoardVO> getList();
	// 03-30 수정
	List<BoardVO> getList(Criteria cri);
	
	int getTotalCnt(Criteria cri);
	
	String deleteFile(AttachFileDTO dto);
}
