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
				userID: encodeURIComponent('${ sessionScope.userID }'),
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
	
	function passwordCheckFunction(){
		var userPassword1 = $('#userPassword1').val();
		var userPassword2 = $('#userPassword2').val();
		if(userPassword1 != userPassword2){
			$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
		}else{
			$('#passwordCheckMessage').html('비밀번호가 체크 완료');
		}
	}
	
	function findFunction(){
		var userID = '${sessionScope.userID}';
		$.ajax({
			type: 'POST',
			url	: './UserFind.ajax',
			data: {userID: userID},
			success: function(result){
				if(result == -1){
		
					failProfile();
				}else{
			
					var data = JSON.parse(result);
					var profile = data.userProfile;
					getProfile(profile);
				}
			}
		});
	}
	
	function getProfile(profile){
		$('#ProfileResult').html(
				'<td style="width:130px; vertical-align: middle;"><h5>프로필 사진</h5></td>'+
				'<td colspan="2" style="text-align: center;">'+ 
				'<img class="media-object img-circle" style="max-width:300px; margin: 0 auto;" src="'+ profile + '">'+
				'</td>' 
				);
		
	}
	
	function failProfile(){
		$('#ProfileResult').html('')
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
	
	
	<div class="container" id="body">
		<form method="post" action="./userProfileUpdateAction.user" enctype="multipart/form-data">
			<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3"><h4>프로필 사진 수정 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>아이디</h5></td>
						<td><h5>${requestScope.user.userID}</h5>
						<input type="hidden" name="userID" value="${requestScope.user.userID}"></td>
					</tr>
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>사진 업로드</h5></td>
						<td colspan="2">
							<input type="file" name="userProfile" class="file">
							<div class="input-group col-xs-12">
								<span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
								<input type="text" class="form-control input-lg" disabled placeholder="이미지를 업로드하세요.">
								<span class="input-group-btn">
									<button class="browse btn btn-success input-lg" type="button"><i class="glyphicon glyphicon-search"></i>파일 찾기 </button>
								</span>
								
							</div>
						
							
						</td>
					</tr>		
							
					<tr id="ProfileResult">			
	
					</tr>
					<tr>
						<td style="text-align: left;" colspan="3"><h5 style="color: red; "></h5><input class="btn btn-success pull-right" type="submit" value="등록"></td>
					</tr>
				</tbody>
			</table>
		</form>
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
						findFunction();
						getInfiniteUnread();
				});
				</script>
			</c:if>
			
			
			<script type="text/javascript">
			$(document).on('click', '.browse', function() {
				var file = $(this).parent().parent().parent().find('.file');
				file.trigger('click');
			});
			$(document).on('change', '.file', function() {
				$(this).parent().find('.form-control').val($(this).val().replace(/C:\\fakepath\\/i, ''));
			});
			</script>

			
	<jsp:include page="footer.jsp"/>
</body>
</html>