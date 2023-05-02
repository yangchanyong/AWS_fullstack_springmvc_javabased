package com.chanyongyang.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j;

// ServerSocket
@Log4j
public class ChatHandler extends TextWebSocketHandler{
	// 접속자 관리 객체
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	@Override // 접속시작
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		log.warn("현재 접속자 수 " + sessions.size());
		log.warn("현재 접속자 정보");
		sessions.forEach(log::warn);
	}
	@Override // 접속종료
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.warn(session.getId() + " 로그아웃");
		sessions.remove(session);
	}

	@Override//			실제할일			현재접속자 정보
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		Gson gson = new Gson();
		Map<?, ?> map = gson.fromJson(msg, Map.class);
		log.warn(map.get("msg"));
		log.warn(map.get("id"));
		log.warn(msg);
		log.warn(session.getAttributes().get("member"));
		for(WebSocketSession s : sessions) {
			s.sendMessage(new TextMessage(session.getId() + " :: " + msg));
		}
	}

	// lifecycle
	// 접속, 실제할일, 종료
	
}
