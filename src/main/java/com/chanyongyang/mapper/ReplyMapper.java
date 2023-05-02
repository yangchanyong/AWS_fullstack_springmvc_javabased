package com.chanyongyang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chanyongyang.domain.ReplyVO;

public interface ReplyMapper {
	int insert(ReplyVO vo);
	
	ReplyVO read(Long rno);
	// 특정 글에 대한것이 필요함(bno) 제일 마지막에 할것
	List<ReplyVO> getList(@Param("bno") Long bno, @Param("rno") Long rno);
	
	int update(ReplyVO vo);
	
	int delete(Long rno);
	
	int deleteByBno(Long bno);
}
