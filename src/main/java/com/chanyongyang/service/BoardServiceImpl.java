package com.chanyongyang.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chanyongyang.controller.UploadController;
import com.chanyongyang.domain.AttachFileDTO;
import com.chanyongyang.domain.AttachVO;
import com.chanyongyang.domain.BoardVO;
import com.chanyongyang.domain.Criteria;
import com.chanyongyang.mapper.AttachMapper;
import com.chanyongyang.mapper.BoardMapper;
import com.chanyongyang.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
@ToString
@Service // bean 등록 commponent scan 필요
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	private final BoardMapper boardMapper;
	private final AttachMapper attachMapper;
	private ReplyMapper replyMapper;
	
	@Override
	@Transactional
	public void register(BoardVO vo) {
		boardMapper.insertSelectKey(vo);
		Long bno = vo.getBno();
		int idx = 0;
		List<AttachVO> list = vo.getAttachs();
//		if(list == null || list.size() == 0) {
//			return;
//		}
		for(AttachVO attach : list) {
			attach.setBno(bno);
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}
	}

	@Override
	public BoardVO get(Long bno) {
		return boardMapper.read(bno);
	}

	@Override
	@Transactional
	public boolean modify(BoardVO vo) {
		attachMapper.deleteAll(vo.getBno());
		int idx = 0;
		List<AttachVO> list = vo.getAttachs();
		for(AttachVO attach : list ) {
			attach.setBno(vo.getBno());
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}
		return boardMapper.update(vo) > 0;
	}

	@Override
	@Transactional
	public boolean remove(Long bno) {
		replyMapper.deleteByBno(bno);
//		04-17 추가
		attachMapper.deleteAll(bno);
		return boardMapper.delete(bno) > 0;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		return boardMapper.getListWithPaging(cri);
	}

	@Override
	public int getTotalCnt(Criteria cri) {
		return boardMapper.getTotalCnt(cri);
	}


	@Override
	public String deleteFile(AttachFileDTO dto) {
		log.info(dto);
		
		String s = UploadController.getPATH() + "/" + dto.getPath() + "/"  + dto.getUuid() + "_" + dto.getName();
		File file = new File(s);
		file.delete();
		if(dto.isImage()) {
			s = UploadController.getPATH() + "/" + dto.getPath() + "/s_"  + dto.getUuid() + "_" + dto.getName();
			file = new File(s);
			file.delete();
		}
		log.info(file);
		return dto.getUuid();
	}

}
