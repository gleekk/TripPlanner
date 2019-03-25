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
				<li class="active"><a href="boardView.board">관광지추천</a></li>
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
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="6"><h4>여행지 추천 게시물 보기</h4></th>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>도시</h5></td>
					<td style="width:150px;"><h5>${requestScope.board.city}</h5> </td>
					
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>관광지</h5></td>
					<td ><h5>${requestScope.board.tourist}</h5> </td>
					
					
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>작성자</h5></td>
					<td><h5>${requestScope.board.userID}</h5> </td>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>제목</h5></td>
					<td colspan="3" ><h5>${requestScope.board.boardTitle}</h5> </td>
					<td style="background-color:#fafafa; color:#000000; width:80px;">
					<a onclick="return confirm('추천하시겠습니까?')" href="./likeAction.board?boardID=${requestScope.board.boardID}"><h5>좋아요</h5></a></td>
					<td><h5>${requestScope.board.likeCount} </h5> </td>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>작성날짜</h5></td>
					<td colspan="3" ><h5>${requestScope.board.boardDate}</h5> </td>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>조회수</h5></td>
					<td><h5>${requestScope.board.boardHit} </h5> </td>
				</tr>
				<tr>
					<td style="vertical-align: middle; min-height:400px; background-color:#fafafa; color:#000000; width:80px;"><h5>글 내용</h5></td>
					<td colspan="5" style="text-align: left; height : 400px;"><h5>${requestScope.board.boardContent}</h5> </td>
				</tr>				
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>첨부파일</h5></td>
					<td colspan="5"><h5><a href="boardDownload.board?boardID=${requestScope.board.boardID}">${requestScope.board.boardFile}</a> </h5> </td>
				</tr>
			</thead>	
			<tbody>
				<tr>
					<td colspan="7" style="text-align: right;">
					<a href="boardView.board" class="btn btn-success ">목록</a>					
					<c:if test="${ sessionScope.userID == requestScope.board.userID }" >
					
					<a href="boardUpdate.board?boardID=${requestScope.board.boardID}" class="btn btn-success ">수정</a>
					<a href="boardDelete.board?boardID=${requestScope.board.boardID}" class="btn btn-danger" onclick="return confirm('정말로 삭제 하시겠습니까?');" >삭제</a>
					
					</c:if>
					</td>					
				</tr>
			</tbody>	
		</table>
		

	<form class="form-horizontal" method="post" action="comment.board" >
		<input type="hidden" name="boardID" value="${requestScope.board.boardID}">
	
		<div class="form-group">
			<label>댓글:</label>
			<textarea class="form-control" rows="3" id="commentContent" name="bcomment"></textarea><br>
			<button type="submit" class="btn pull-right">등록</button>
		</div>
	</form>
	
	
	<c:choose>
		<c:when test="${requestScope.commentList.size() == 0}" >
			작성된 댓글이 없습니다.
		</c:when>
		<c:otherwise>
			<c:forEach var="comments" items="${requestScope.commentList}" varStatus="status">		
		<div class="container">
			<hr>
			<div class="form-group">
				<label>&lt댓글&gt<br/></label>	
				<label>작성자:${comments.userID }</label>	
		  	 	<p class="card-text">${comments.bcomment}</p><br>
		  	 	<c:if test="${comments.userID == sessionScope.userID }" >
				<a onclick="return confirm('정말로 삭제 하시겠습니까?')" href="delcomment.board?commentsNo=${comments.commentsNo}&boardID=${comments.boardID}" class="btn btn-danger pull-right">삭제</a>
				</c:if>
			</div>
		</div>
	
			</c:forEach>
		</c:otherwise>
	</c:choose>





		
		
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