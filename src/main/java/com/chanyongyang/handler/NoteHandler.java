package com.chanyongyang.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chanyongyang.domain.MemberVO;
import com.chanyongyang.domain.NoteVO;
import com.chanyongyang.service.NoteService;
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j;

// ServerSocket
@Log4j
@EnableWebSocket
public class NoteHandler extends TextWebSocketHandler{
	// 접속자 관리 객체
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	@Autowired
	private NoteService noteService;
	
	@Override // 접속시작
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		
		log.warn(sessions.stream().map(s->getIdBySession(s)).collect(Collectors.toList()));
	}
	@Override // 접속종료
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

	@Override//			실제할일			현재접속자 정보
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String receiver = message.getPayload();
		String sender = getIdBySession(session);
		List<NoteVO> list0 = noteService.getSendList(sender);
		List<NoteVO> list1 = noteService.getReceiveList(receiver);
		List<NoteVO> list2 = noteService.getReceiveUncheckedList(receiver);
		
		Map<String, Object> map = new HashMap<>();
		map.put("sendList", list0);
		map.put("receiveList", list1);
		map.put("receiveUncheckedList", list2);
		map.put("sender", sender);
		
		
		Gson gson = new Gson();
		for(WebSocketSession s : sessions) {
			if(receiver.equals(getIdBySession(s)) || session == s) {
				s.sendMessage(new TextMessage(gson.toJson(map)));
			}
		}
	}
	private String getIdBySession(WebSocketSession session) {
		Object obj = session.getAttributes().get("member");
		String id = null;
		if(obj != null && obj instanceof MemberVO) {
			id = ((MemberVO)obj).getUserid();
		}
		return id;
	}
}
