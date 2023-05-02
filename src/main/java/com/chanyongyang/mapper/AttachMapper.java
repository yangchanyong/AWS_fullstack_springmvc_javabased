package com.chanyongyang.mapper;

import java.util.List;

import com.chanyongyang.domain.AttachVO;

public interface AttachMapper {
	void insert(AttachVO vo);
	
	void delete(String uuid);
	
	List<AttachVO> findBy(Long bno);
	
	void deleteAll (Long bno);
//	04-18 추가
	List<AttachVO> getOldFiles();
}
