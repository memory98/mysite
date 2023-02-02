<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newline","\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="hidden" name="a" value="search">
					<input type="text" id="kwd" name="kwd" value="${keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>		
					<c:set var="pageSize" value="${10}"></c:set>
					<!--<c:set var="count" value="${fn:length(list)}"></c:set> -->
					<c:forEach items="${list}" var="vo" varStatus="status">
						
						<tr>
							<td>${boardCnt - pageSize * (page-1)-status.index}</td>
							<td style="text-align:left; padding-left:${vo.depth*20}px">
							<c:if test="${vo.depth>0 }">
								<img src="${pageContext.request.contextPath }/assets/images/reply.png">
							</c:if>
								<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title}</a></td>
							<td>${vo.userName}</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
								<td>
									<c:if test="${authUser.no == vo.userNo}">
										<a href="${pageContext.request.contextPath}/board?a=delete&no=${vo.no}" class="del">삭제</a>
									</c:if>
								</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li>
						<c:choose>
							<c:when test="${page-1 >0}">
								<a href="${pageContext.request.contextPath }/board?a=list&page=${page-1}&kwd=${keyword}">◀</a>
							</c:when>
							<c:otherwise>
								<p>◀</p>
							</c:otherwise>
						</c:choose>
						</li>
						<c:choose>
							<c:when test="${boardCnt%pageSize==0 }">
								<c:set var="end" value="${boardCnt/pageSize }"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="end" value="${boardCnt/pageSize+1 }"></c:set>
							</c:otherwise>
						</c:choose>
						<c:forEach var="i" begin="1" end="${end }" step="1">
							<c:choose>
								<c:when test="${page ==i}">
									<li class="selected">${i }</li>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath }/board?a=list&page=${i}&kwd=${keyword}">${i }</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
<!-- 						<li class="selected"><a href="">1</a></li>
						<li>2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li> -->
						<li>
							<c:choose>
									<c:when test="${page+1 <boardCnt/pageSize+1}">
									<a href="${pageContext.request.contextPath }/board?a=list&page=${page+1}&kwd=${keyword}">▶</a>
								</c:when>
								<c:otherwise>
									<p>▶</p>
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</div>					
				<!-- pager 추가 -->

				<div class="bottom">
					<c:if test="${not empty authUser}">
						<a href="${pageContext.request.contextPath}/board?a=writeform&no=${-1}" id="new-book">글쓰기</a>
					</c:if>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>