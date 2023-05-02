package com.chanyongyang.domain;

import lombok.Data;

@Data
public class PageDto {
	
	// 하단에 출력 될 페이지 사이즈
	private int pageCount = 10;
	// 시작 페이지 숫자
	private int startPage;
	// 종료 페이지 숫자 
	private int endPage;
	
	// 게시글의 총 갯수
	private int total;
	
	// next, prev
	private boolean prev;
	private boolean next;
	
	private boolean doublePrev;
	private boolean doubleNext;
	// criteria
	private Criteria cri;
	
	public PageDto(int total, Criteria cri) {
		this(10, total, cri);
	}
	
	public PageDto(int pageCount, int total, Criteria cri) {
		this.pageCount = pageCount;
		this.total = total;
		this.cri = cri;
		
		endPage = (cri.getPageNum() + (pageCount-1)) / pageCount * pageCount;
		startPage = endPage - (pageCount-1);
		int realEnd = (total + (cri.getAmount() - 1)) / cri.getAmount();
		if(endPage > realEnd) {
			endPage = realEnd;
		}
		prev = cri.getPageNum() > 1;
		next = cri.getPageNum() < realEnd;
		
		doublePrev = startPage > 1;
		doubleNext = endPage < realEnd;
	}
	
	
	
	
	// 예정 <<, >>
	
}
