package com.chanyongyang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chanyongyang.domain.NoteVO;
import com.chanyongyang.mapper.NoteMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService{
	private NoteMapper mapper;
	
	@Override
	public int send(NoteVO vo) {
		return mapper.insert(vo);
	}

	@Override
	public NoteVO get(Long noteno) {
		return mapper.selectOne(noteno);
	}

	@Override
	public int receive(Long noteno) {
		return mapper.update(noteno);
	}

	@Override
	public int remove(Long noteno) {
		return mapper.delete(noteno);
	}

	@Override
	public List<NoteVO> getSendList(String id) {
		return mapper.sendList(id);
	}

	@Override
	public List<NoteVO> getReceiveList(String id) {
		return mapper.receiveList(id);
	}

	@Override
	public List<NoteVO> getReceiveUncheckedList(String id) {
		return mapper.receiveUncheckedList(id);
	}

}
