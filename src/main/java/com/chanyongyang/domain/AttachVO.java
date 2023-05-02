package com.chanyongyang.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true) // 조상클래스의 toString까지 같이 표기함
@Alias("attach")
public class AttachVO extends AttachFileDTO{
	private Long bno;
}
