<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage = "errorPage.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/custom.css">
<title>Trip_Planner</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script type="text/javascript">
	function getUnread(){
		$.ajax({
			type: 'POST',
			url	: './ChatUnread.ajax',
			data: {
				userID: encodeURIComponent('${sessionScope.userID}'),
				},
			success: function(result){
				if(result >= 1){
					showUnread(result);
				}else{
					showUnread('');
				}
			}
		});			
	}
	function getInfiniteUnread(){
		setInterval(function() {
			getUnread();			
		}, 4000);
	}
	function showUnread(result){
		$('#unread').html(result);
	}
	
	function chatBoxFunction(){
		var userID = '${sessionScope.userID}'
		$.ajax({
			type: 'POST',
			url	: './ChatBox.ajax',
			data: {
				userID: encodeURIComponent(userID),
				},
			success: function(data){
				if(data == "")return;
				$('#boxTable').html('');
				var parsed = JSON.parse(data);
				var result = parsed.result;
				for(var i = 0 ; i < result.length; i ++){					
					if(result[i][0].value == userID){
						result[i][0].value = result[i][1].value;
					}else{
						result[i][1].value = result[i][0].value;
					}
					addBox(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value, result[i][5].value);
				}
			}
		});			
	}
	
	function addBox(lastID, toID, chatContent, chatTime, unread, profile){
		$('#boxTable').append('<tr onclick="location.href=\'chat.chat?toID=' + encodeURIComponent(toID) + '\'">'+
			'<td style="width: 150px;">' + 
			'<img class="media-object img-circle" style="max-width:40px; max-height:40px; margin: 0 auto;" src="'+ profile + '">'+
			'<h5>' + lastID + '</h5></td>' +
			'<td>' + 
			'<h5>' + chatContent +
			'<span class="label label-info">'+ unread + '</span></h5>' +
			'<div class="pull-right">' + chatTime + '</div>' + 
			'</td>'+
			'</tr>');
	}
	function getInfiniteBox(){
		setInterval(function() {
			chatBoxFunction();			
		}, 3000);
	}
	
</script>
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.user">Trip_Planner</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="index.user">메인</a></li>
				<li><a href="step1.trip">여행하기</a></li>
				<li><a href="boardView.board">관광지추천</a></li>
				<li><a href="boardView.myboard">자유게시판</a></li>
				<li><a href="gowithView.with">동행구하기</a></li>				
				<li class="active"><a href="box.chat">메시지함 <span id="unread" class="label label-info"></span></a></li>

			</ul>
				<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">회원관리<span class="caret"></span>
				 	</a>
				 	<ul class="dropdown-menu">
				 		<li><a href="update.user">회원정보수정</a></li>
				 		<li><a href="profileUpdate.user">프로필 수정</a></li>
				 		<li><a href="step4.trip">여행계획보기</a></li>
				 		<li><a href="myPage.myboard?userID=${ sessionScope.userID }">마이페이지</a></li>
				 		<li><a href="find.chat">친구찾기</a></li>
						<li><a href="logout.user">로그아웃</a> </li>
						<li><a href="datamineAction.user">사이트 분석</a></li>
						
					</ul>  
				</li>
			</ul>
		</div>
	</nav>
	
	<div class="container" id="body">
		<table class="table" style="margin:0 auto;">
			<thead>
				<tr>
					<th><h4>주고받은 메시지 목록</h4></th>
				</tr>
			</thead>
			<div style="overflow-y: auto; width:100%; max-height:450px;"> 
				<table class="table table-bordered table-hover" style="text-align:center; border:1px solid #dddddd; margin: 0 auto;">
					<tbody id="boxTable">
					
					</tbody>
				</table>
			</div>			
		</table>
	</div>
	
	
		<c:if test="${ sessionScope.messageContent != null }" >
			<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
				 <div class="vertical-alignment-helper"> 
				 	<div class="modal-dialog vertical-align-center">
				 		<div class="modal-content">
				 			<div class="modal-header panel-heading">
				 				<button type="button" class="close" data-dismiss="modal">
				 					<span aria-hidden="true">&times;</span>
				 					<span class="sr-only">Close</span>
				 				</button>
				 				<h4 class="modal-title" style="text-align: left;">
				 					${sessionScope.messageType}
				 				</h4>
				 			</div>
				 			<div class="modal-body">
				 				${sessionScope.messageContent}
				 			</div>
				 			<div class="modal-footer">
				 				<button type="button" class="btn btn-success" data-dismiss="modal">확인</button>
				 			</div>
				 		</div>
				 	</div>
				 </div>
			</div>
			<script>
				$('#messageModal').modal("show"); 
			</script>
						<c:remove var="messageType" scope="session" />
						<c:remove var="messageContent" scope="session" />
			
			</c:if>
			
			
			<c:if test="${ sessionScope.userID != null }" >
				<script type="text/javascript">
					$(document).ready(function(){
						getUnread();	
						getInfiniteUnread();
						chatBoxFunction();
						getInfiniteBox();
				});
				</script>
			</c:if>
	<jsp:include page="footer.jsp"/>
</body>
</html>