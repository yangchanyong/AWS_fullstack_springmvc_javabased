package com.chanyongyang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chanyongyang.domain.BoardVO;
import com.chanyongyang.domain.Criteria;

public interface BoardMapper {
	// 글 목록 조회
	List<BoardVO> getList();
	List<BoardVO> getListWithPaging(Criteria criteria);
	
	// 글 등록
	void insert(BoardVO vo);
	
	// 글 등록
	void insertSelectKey(BoardVO vo);
	
	// 조회
	BoardVO read(Long bno);
	
	// 삭제
	int delete(Long bno);
	
	// 수정
	int update(BoardVO vo);
	
	// 갯수
	int getTotalCnt(Criteria cri);
	
	// 댓글 갯수 반영
	void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
