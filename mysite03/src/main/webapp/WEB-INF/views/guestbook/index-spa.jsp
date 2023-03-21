<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script></head>
<script>
/*
var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"	
});
var listTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"	
});

*/
var render = function(vo, mode) {
	var htmls = "";
	htmls = 
		"<li id='list-li"+vo.no+"' data-no='"+vo.no+"'>"+
		 "<strong>"+vo.name+"</strong>"+
		 "<p>"+vo.message+"</p>"+
		 "<strong></strong>"+
		 "<a href=''>삭제</a>"+
		 "</li>";
	// var htmls = listItemTemplate.render(vo);
	$("#list-guestbook")[mode?"prepend":"append"](htmls);
//	$listLi.data("no");
}	
var fetch = function() {
	$.ajax({
		url: "${pageContext.request.contextPath }/guestbook/api?sno=0",
		type: "get",
		dataType: "json",
		success: function(response) {
			if(response.result ==='fail') {
				console.error();
				return ;
			}
			response.data.forEach(function(vo) {
				render(vo,false);
			});
		}
	});
}

var success_addList = function(data) {
	$("#input-name").val("");
	$("#input-password").val("");
	$("#tx-content").val("");
	
	console.log(data);
	render(data,true);
}

$(function() {
	$("#add-form").submit(function(event) {
		event.preventDefault();
		
		var vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();

		console.log("vo.name : ",vo.name);
		console.log("vo.password : ",vo.password);
		console.log("vo.message : ",vo.message);
		if(vo.name==='') {
			return ;
		}
		
		if(vo.password==='') {
			return ;
		}
		
		if(vo.message==='') {
			return ;
		}
		$.ajax({
			url: "${pageContext.request.contextPath }/guestbook/api/add",
			type: "post",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(vo),
			success: function(response) {
				if(response.result ==='fail') {
					console.error();
					return ;
				}

				success_addList(response.data);
			}
		});
	});
	//삭제 다이알로그 jQuery 객체 미리 만들기
	var $dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function() {
				console.log("ajax 삭제하기");
				var no = $("#hidden-no").val();
				var password = $("#password-delete").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/guestbook/api/"+no,
					type: "delete",
					dataType: "json",
					contentType: "application/x-www-form-urlencoded",
					data: "password=" + password,
					success: function(response) {
						$("#password-delete").val("");
						if(response.result ==='fail') {
							// $("#dialog-message p").text("비밀번호가 틀렸습니다.");
							$(".error").show();
							console.error();
							console.log("fail");
							return ;
						}

						console.log("success");
						$("#dialog-delete-form").dialog('close');
						$("#list-li"+no).remove();
					}
				});
			},
			"취소": function() {
//				console.log("삭제 다이알로그의 폼 데이터 리셋하기");
				$(".error").hide();
				$(this).dialog('close');
			}
		}
	});
	// 메세지 삭제 버튼 click 이벤트 처리()
	$(document).on('click', "#list-guestbook li", function(event) {
		event.preventDefault();
		$("#hidden-no").val($(this).data("no"));
		$dialogDelete.dialog('open');
	});
	
	$(window).scroll(function() {	
	});
// 최초 리스트 가져오기
fetch();
})
</script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" method="post" action="${pageContext.request.contextPath }/guestbook/spa/add">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form >
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit"  tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
				<div id="dialog-message" title="" style="display:none">
  					<p style=""></p>
				</div>	
			</div>

		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>