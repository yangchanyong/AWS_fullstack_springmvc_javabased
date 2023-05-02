<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<jsp:include page="../includes/header.jsp" />

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-2 font-weight-bold text-primary float-left">Board List Page</h6>
                            <a href="register${page.cri.queryString}" class="btn btn-primary float-right btn-sm">Register New Board</a>
                        </div>
                        <div class="card-body">
                        
                    	    <!-- 검색창 -->
                    <c:set var="types" value="T,C,W,TC,TW,CW,TCW"></c:set>
					<form class="my-3 justify-content-center d-flex">
                    	<div class="col-md-8 col-lg-6 input-group">
                       		<select class="form-control-sm mr-3" name="type">
                       		<c:forTokens items="제목,내용,작성자,제목+내용,제목+작성자,내용+작성자+제목+내용+작성자" delims="," varStatus="stat" var="it">
                       		 <option value="${types.split(',')[stat.index]}" ${types.split(',')[stat.index] eq page.cri.type ? 'selected' : ''}>${it}</option>
                       		</c:forTokens>
                    	    		</select>
                            <input name="keyword" value="${page.cri.keyword}" type="text" class="form-control-sm bg-light border-0 small w-50" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-primary btn-sm">
                                    <i class="fas fa-search fa-sm"></i>
                                </button>
                            </div>
                        </div>
                        <input type="hidden" name="pageNum" value="1">
                        <input type="hidden" name="amount" value="${page.cri.amount}">
                    </form>
                    <!-- 검색창 끝 -->
                    
                    
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>번호</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>작성일</th>
                                            <th>수정일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${list}" var="board">
											<tr>
												<td><c:out value="${board.bno}"></c:out> </td>
												<td><a href="get${page.cri.fullQueryString}&bno=${board.bno}"><c:out value="${board.title}"></c:out></a><span class="text-muted">[${board.replycnt}]</span></td>
												<td><c:out value="${board.writer}"></c:out> </td>
												<td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd"/> </td>
												<td><fmt:formatDate value="${board.updatedate}" pattern="yyyy-MM-dd"/> </td>
											</tr>
										</c:forEach>                                  
                                    </tbody>
                                </table>
                            </div>
                                    <ul class="pagination mt-3 justify-content-center">
        <c:if test="${page.doublePrev}">
          <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.startPage - 1}"><i class="fas fa-angle-double-left"></i></a></li>
        </c:if>
        <c:if test="${page.prev}">
          <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.cri.pageNum - 1}"><i class="fas fa-angle-left"></i></a></li>
        </c:if>
        
        <c:forEach begin="${page.startPage}" end="${page.endPage}" var="i">
          <li class="page-item ${page.cri.pageNum == i ? 'active' : ''}"><a class="page-link" href="list${page.cri.queryString}&pageNum=${i}">${i}</a></li>
		</c:forEach>
		
        <c:if test="${page.next}">
          <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.cri.pageNum + 1}"><i class="fas fa-angle-right"></i></a></li>
        </c:if>
        <c:if test="${page.doubleNext}">
          <li class="page-item"><a class="page-link" href="list${page.cri.queryString}&pageNum=${page.endPage + 1}"><i class="fas fa-angle-double-right"></i></a></li>
        </c:if>
        </ul>
                        </div>
                    </div>
				
                </div>
                <!-- /.container-fluid -->

<script>
/* 게시글 등록시 modal */
	var result = '<c:out value="${result}" />';
	$(function() {
		if(!result || history.state) {
			return; 
		}
		if(result !== 'success') {
			$("#myModal .modal-body").html("게시글 " + result + "번이 등록되었습니다.")
		}			
		$("#myModal").modal("show");
		// history 초기화
		history.replaceState({}, null, null);
	});
	/* modal end */
	console.log(result);
	
</script>

<jsp:include page="../includes/footer.jsp" />
