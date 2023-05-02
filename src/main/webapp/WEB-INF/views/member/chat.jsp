<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>2023. 4. 12.오후 12:10:23</title>
<style>
	textarea {resize: none;}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
	<c:if test="${empty member}">
		<form method="post" action="login">
			<input name="id">
			<input name="pw" type="password">
			<button>로그인</button>
			<p>${msg}</p>
		</form>
	</c:if>
	<c:if test="${not empty member}">
		<p>로그인 됨</p>
		<p>${member.name}/ ${member.id}</p>
		<a href="logout">로그아웃</a>
		<h1>chat client</h1>
		<form name="frm">
			<textarea rows="10" readonly name="area"></textarea>
			<br> <input name="message" autofocus>
			<button>전송</button>
		</form>
		<script>
		var ws = new WebSocket("ws://localhost/chat");
		ws.onopen = function(ev) {
			console.log("연결 완료", ev);
		}
		ws.onclose = function(ev) {
			console.log("연결 종료", ev);
		}
		ws.onmessage = function(ev) {
			console.log(ev.data)
			frm.area.value = frm.area.value + ev.data + "\n";
		}
		$(function() {
			$(document.frm).submit(function() {
				event.preventDefault();
				var msg = this.message.value;
				this.message.value = "";
				// 현재 로그인된 사용자의 id가 들어갈 예쩡
				var obj = {id:'ycy', msg:msg};
				ws.send(JSON.stringify(obj));
				console.log(msg);
				
			})
		})
	</script>
	</c:if>

</body>
</html>