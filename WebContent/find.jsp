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

	function findFunction(){
		var userID = $('#findID').val();
		$.ajax({
			type: 'POST',
			url	: './UserFind.ajax',
			data: {userID: userID},
			success: function(result){
				if(result == -1){
					$('#checkMessage').html('친구를 찾을수 없습니다.');
					$('#checkType').attr('class', 'modal-content panel-warning');
					failFriend();
				}else{
					$('#checkMessage').html('친구 찾기에 성공했습니다.');
					$('#checkType').attr('class', 'modal-content panel-success');
					var data = JSON.parse(result);
					var profile = data.userProfile;
					getFriend(userID, profile);
				}
				$('#checkModal').modal("show");
			}
		});
	}
	
	function getFriend(findID, profile){
		$('#friendResult').html('<thead>' +
				'<tr>' +
				'<th><h4>검색 결과</h4></th>' +
				'</tr>' +
				'</thead>' +
				'<tbody>' +
				'<tr>' +
				'<td style="text-align: center;">'+ 
				'<img class="media-object img-circle" style="max-width:300px; margin: 0 auto;" src="'+ profile + '">'+
				'<h3>' + findID + '</h3>'+
				'<a href="myPage.myboard?userID=' + encodeURIComponent(findID) + '" class="btn btn-success pull-right">' + encodeURIComponent(findID) + '님의 마이페이지</a>'+
				'<a href="chat.chat?toID=' + encodeURIComponent(findID) + '" class="btn btn-success pull-right">'+'메시지 보내기</a></td>' +
				'</tr>' +
				'</tbody>');
		
	}
	function failFriend(){
		$('#friendResult').html('')
	}
	
	
	function getUnread(){
		$.ajax({
			type: 'POST',
			url	: './ChatUnread.ajax',
			data: {
				userID: encodeURIComponent('${ sessionScope.userID == null }'),
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
				<li><a href="box.chat">메시지함 <span id="unread" class="label label-info"></span></a></li>

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
	
	<div id="body">
	
	<div class="container">
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2"><h4>검색으로 친구찾기</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width:110px;"><h5>친구 아이디</h5></td>
						<td><input class="form-control" type="text" id="findID" maxLength="20" placeholder="찾을 아이디를 입력하세요"></td>
					</tr>
					<tr>
						<td colspan="2"><button class="btn btn-success pull-right" onclick="findFunction();">검색</button> </td>
					</tr>									
				</tbody>
		</table>
	</div>
	
	<div class="container">
		<table id="friendResult" class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
				
				
				
		</table>
	</div>
	
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
			
			<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
				 <div class="vertical-alignment-helper"> 
				 	<div class="modal-dialog vertical-align-center">
				 		<div id="checkType" class="modal-content panel-info">
				 			<div class="modal-header panel-heading">
				 				<button type="button" class="close" data-dismiss="modal">
				 					<span aria-hidden="true">&times;</span>
				 					<span class="sr-only">Close</span>
				 				</button>
				 				<h4 class="modal-title" style="text-align: left;">
				 					확인 메시지
				 				</h4>
				 			</div>
				 			<div class="modal-body" id="checkMessage">
				 			</div>
				 			<div class="modal-footer">
				 				<button type="button" class="btn btn-success" data-dismiss="modal">확인</button>
				 			</div>
				 		</div>
				 	</div>
				 </div>
			</div>
			
		<c:if test="${ sessionScope.userID != null }" >
					
				<script type="text/javascript">
					$(document).ready(function(){
						getUnread();	
						getInfiniteUnread();
				});
				</script>
			</c:if>
				<jsp:include page="footer.jsp"/>
</body>
</html>