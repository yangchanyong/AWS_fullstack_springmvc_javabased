package com.chanyongyang.domain;

import java.util.Date;

import lombok.Data;

@Data
public class NoteVO {
	private Long noteno;
	private String sender;
	private String receiver;
	private Date sdate;
	private Date rdate;
	private String message;
}
