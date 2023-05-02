console.log("Reply Module");
var xhr = new XMLHttpRequest();
// xhr.open();
// xhr.send();
var replyService = (function() {
  // 댓글추가의 목적
  function add(reply, callback, error) {
    console.log("add() :: " + reply);
    console.log(reply);
    $.post({
    	url:cp + "/replies/new",
    	data : JSON.stringify(reply),
    	dataType : "json",
    	contentType : "application/json; charset=utf-8"
    })
    .done(function(data) {
      if(callback) {
        callback(data);
      }
    })
    .fail(function(xhr) {
    	console.log(xhr)
    })
  }
  // 댓글 단일조회
  function get(rno, callback) {
    var url = cp + "/replies/" + rno;
    console.log(url);
    $.getJSON(url)
    .done (function (data) {
        if(callback) {
          callback(data);
        }
    })
  }
  // 댓글 목록조회
  function getList(param, callback, error) {
    // var url = "/replies/list/" + param.bno + "/" + (param.rno ? param.rno : "" );
     var url = "/replies/list/" + param.bno + "/" + (param.rno || "");
    // nullish
    // var url = "/replies/list/" + param.bno + "/" + (param.rno ?? "");
//      url: url, url을 앞으로 땡겨도 같은 결과가 나옴
//      dataType : "json",
    console.log(url);
    $.getJSON(url)
    .done (function (data) {
        if(callback) {
          callback(data);
        }
    })
    .fail(function(xhr) {
    	if(error) {
    		error(xhr);
    	}
    })
  }
  // 댓글 수정
  function modify(reply, callback, error) {
    console.log("modify()");
    console.log(reply);
    $.ajax({
    	url: cp + "/replies/" + reply.rno,
      method : 'put',
    	data : JSON.stringify(reply),
    	dataType : "json",
    	contentType : "application/json; charset=utf-8"
    })
    .done(function(data) {
      if(callback) {
        callback(data);
      }
    })
    .fail(function(xhr) {
    	console.log(xhr)
    })
  }
  
  // 댓글 단일조회
  function get(rno, callback) {
    var url = cp + "/replies/" + rno;
    console.log(url);
    $.getJSON(url)
    .done (function (data) {
        if(callback) {
          callback(data);
        }
    })
  }
  // 댓글 삭제
  function remove(reply, callback, error) {
    $.ajax({
    	url : cp + "/replies/" + reply.rno,
      method : 'delete',
      data : JSON.stringify(reply),
      dataType : 'json',
      contentType : "application/json; charset=utf-8"
    })
    .done(function(data) {
      if(callback) {
        callback(data);
      }
    })
    .fail(function(xhr) {
    	console.log(xhr)
    })
  }

  return {
    add:add,
    getList:getList,
    get:get,
    remove:remove,
    modify:modify
  };
})();