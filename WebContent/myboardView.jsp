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
	
	function gowithchat(toID){
 		var toID=toID;
 		$.ajax({
 			type : 'POST',
 			url : './GowithChat.ajax',
 			data : { toID : toID},
 			datatype : 'json',
 			success : function(data){
 				var chat = JSON.parse(data);
 				if(chat.no=="-1"){
 					$("#chatbtn").css("display", "none");
 					$("#chatmsg").css("display", "");
 					$("#chatmsg").val("자기자신에게는 메세지를 보낼수 없습니다.");
 					$("#chattable").css("display", "none");	
 				}else{
 				$("#chatbtn").css("display", "");
 				$("#chatmsg").css("display", "none");
 				$("#chattable").css("display", "");
 				$("#chatimg").attr("src",chat.toProfile);
 				$("#toID").val(chat.toID);
 				}
 			}
			})
		};
		
		
		function submitFunction(){
			var fromID = '${ sessionScope.userID }';
			var toID = $('#toID').val();
			var chatContent = $('#chatContent').val();
			$.ajax({
				type: 'POST',
				url	: './ChatSubmit.ajax',
				data: {
					fromID: encodeURIComponent(fromID),
					toID: encodeURIComponent(toID),
					chatContent: encodeURIComponent(chatContent)
				},
				success: function(result){
					if(result == 1 ){
						alert("메세지 전송에 성공하였습니다");
						$("#chatModal").hide();
					}else if (result == 0){
						alert("메세지를 입력해주세요");
					}
					$('#chatContent').val('');
				}
			});			
		}		
	
</script>
</head>
<style>
.dropdown1 {
	position: absolute;
	padding-left: 1.5rem;
	/* display: inline-block; */
}

.dropdown1-content {
	display: none;
	left: 1rem;
	top: 1rem;
	position: absolute;
	border: thin;
	z-index: 1;
	background-color: rgba(255, 255, 255, 0.5);

}

.dropdown1-content a {
	color: black;
	padding: 5px 10px;
	text-decoration: none;
	display: block;
	width:210px;
}

.dropdown1-content a:hover {
	background-color: rgba(255, 255, 255, 0.6);
}

.dropdown1:hover .dropdown1-content {
	display: block;
}

.btn1:hover, .dropdown1:hover .btn {
	background-color: #0b7dda;
}


</style>
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
				<li class="active"><a href="boardView.myboard">자유게시판</a></li>
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
	
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="8"><h4>자유 게시판</h4></th>
				</tr>
				<tr>
					<th style="background-color:#fafafa; color:#000000; width:70px;"><h5>번호</h5></th>
					<th style="background-color:#fafafa; color:#000000; width:100px;"><h5>분류</h5></th>
					<th style="background-color:#fafafa; color:#000000;"><h5>제목</h5></th>
					<th style="background-color:#fafafa; color:#000000; width:120px;"><h5>작성자</h5></th>
					<th style="background-color:#fafafa; color:#000000; width:120px;"><h5>작성 날짜</h5></th>
					<th style="background-color:#fafafa; color:#000000; width:80px;"><h5>조회수</h5></th>
					<th style="background-color:#fafafa; color:#000000; width:80px;"><h5>추천수</h5></th>
					
				</tr>	
			</thead>	
			<tbody>
	
		<c:forEach var="board" items="${requestScope.boardList}" varStatus="status">
			<tr>
				<td>${ board.myboardID }</td>
				<td>${ board.mycategory }</td>
				<td style="text-align:left;">
				<a href="boardShow.myboard?myboardID=${board.myboardID}">
				
			
			<c:choose>
				<c:when test="${board.myboardAvailable==0}" >
					(삭제된 게시물입니다.)
				</c:when>
				<c:otherwise>
					${board.myboardTitle}
				</c:otherwise>
			</c:choose>
				</a></td>
					<td>
						<div class="dropdown1">${ board.userID }
							<div class="dropdown1-content">
								<a href="myPage.myboard?userID=${ board.userID }" class="dropdown-item">${ board.userID }님의 마이페이지</a>
								<a onclick="gowithchat('${ board.userID }')" data-target="#chatModal" data-toggle="modal" class="dropdown-item">
								${ board.userID }님에게 메세지 보내기</a>
							</div>
						</div>					
					</td>
					<td>${ board.myboardDate }</td>
					<td>${ board.myboardHit }</td>
					<td>${ board.mylikeCount }</td>
					
				</tr>
		</c:forEach>
	
				<tr>
					<td colspan="8"><a href="boardWrite.myboard?pagename=myboard" class="btn btn-success pull-right" type="submit">글쓰기</a>
					<ul class="pagination" style="margin: 0 auto;">
	
			 <c:choose>
				<c:when test="${requestScope.startPage != 1}" >
					<li>
						<a href="boardView.myboard?pageNumber=${requestScope.startPage-1}">
							<span class="glyphicon glyphicon-chevron-left"></span>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<span class="glyphicon glyphicon-chevron-left" style="color:gray;"></span>
					</li>
				</c:otherwise>
			</c:choose>

			<c:forEach var="num" begin="${requestScope.startPage}" end="${requestScope.pageNumber-1}" step="1" varStatus="status">
				<li>
					<a href="boardView.myboard?pageNumber=${num}&searchType=${requestScope.searchType}">${num}</a>
				</li>
			</c:forEach>
		
			<li class="active"><a  class="btn btn-success" href="boardView.myboard?pageNumber=${requestScope.pageNumber}&searchType=${requestScope.searchType}">${requestScope.pageNumber}</a></li>

			<c:forEach var="num" begin="${requestScope.pageNumber+1}" end="${requestScope.pageNumber+requestScope.targetPage}" step="1" varStatus="status">
				<c:if test="${num < (requestScope.startPage+10)}" >
					<li>
						<a href="boardView.myboard?pageNumber=${num}&searchType=${requestScope.searchType}">${num}</a>
					</li>
				</c:if>
			</c:forEach>
			
			<c:choose>
				<c:when test="${(requestScope.pageNumber+requestScope.targetPage) > (requestScope.startPage+9)}" >
					<li>
						<a href="boardView.myboard?pageNumber=${requestScope.startPage+10}">
							<span class="glyphicon glyphicon-chevron-right"></span>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<span class="glyphicon glyphicon-chevron-right" style="color:gray;"></span>
					</li>
				</c:otherwise>
			</c:choose>
			</ul>
					</td>					
				</tr>
			</tbody>	
		</table>
		
		<div class="center" style="text-align:center; margin-top: 2em;">
		<form action="boardSearch.myboard" method="get" class="form-inline mt-3">
			<select name="searchType" class="form-control mx-1 mt-2">
				<option value="최신순">최신순</option>
				<option value="추천순"
				<c:if test="${requestScope.searchType == '추천순'}" >
					selected
				</c:if>
				>추천순</option>
				<option value="조회수"
				<c:if test="${requestScope.searchType == '조회수'}" >
					selected
				</c:if>
				>조회수</option>
				
			</select> 
			
			<input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요">
			<button type="submit" class="btn btn-success mx-1 mt-2">검색</button>
		</form>
	</div>	
</div>	

	<div class="modal" id="chatModal" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" >
			<div class="modal-content">
				<div class="modal-header">
					메세지보내기
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body">
					<input type="text" id="chatmsg" style="display:none; border:none" size="50">
					<table id="chattable">
						<tr>
							<td rowspan="2"><img id="chatimg" src="" width="170" height="170"></td>
							<td id="td1"> to.<input type="text" id="toID" style="border:none"> </td>
						</tr>
						<tr>
							<td id="td">
							<textarea style="height:150px; width: 370px;" id="chatContent" class="form-control" placeholder="메시지를 입력하시오" maxlength="100" onKeyDown="javascript:if (event.keyCode == 13) submitFunction();"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="chatbtn" class="btn btn-default pull-right" onclick="submitFunction()">전송</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
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