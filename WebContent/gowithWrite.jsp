<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage = "errorPage.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Trip_Planner</title>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/custom.css">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link href="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
<script src="./js/bootstrap.min.js"></script>
<script type="text/javascript">

   $(document).ready(function(){
         var   minDate = new Date();
      $("#depart").datepicker({
         showAnim: 'drop',
         numberOfMonth: 1,
         minDate: minDate,
         maxDate: '22/07/2019',
         dateFormat:'yy-mm-dd',
         onClose:function(selectedDate){
            $('#return').datepicker("option","minDate",selectedDate);
         }
      });
      
      $("#return").datepicker({
         showAnim: 'drop',
         numberOfMonth: 1,
         minDate: minDate,
         dateFormat:'yy-mm-dd',
         onClose:function(selectedDate){
            $('#depart').datepicker("option","maxDate",selectedDate);
         }
      });
   });

	function writecheck(){
		var title=$("#title").val();
		var content=$("#content").val();
		var date1=$("#depart").val();
		var date2=$("#return").val();
		var mem = $(":input:radio[name=mem]:checked").val();
		var age = $(":input:radio[name=age]:checked").val();
		var destination=$("#destination").val();
		
		if(title==""){
			alert("제목을 입력해 주세요");
			return false;
		}
		
		if(content==""){
			alert("내용을 입력해 주세요");
			return false;
		}
		
		if(date1=="" || date2==""){
			alert("출발 및 도착 날자를 모두 선택해 주세요");
			return false;
		}
		
		if(mem==undefined){
			alert("인원을 선택해 주세요");
			return false;
		}
		
		if(destination ==""){
			alert("도착지를 입력해 주세요");
			return false;
		}
		
		if(age==undefined){
			alert("연령대를 선택해 주세요");
			return false;
		}
		
	}
   
</script>
</head>
<style>
form{
width: 70%;
margin:100px auto;
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
				<li><a href="boardView.myboard">자유게시판</a></li>
				<li class="active"><a href="gowithView.with">동행구하기</a></li>								
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
		<form method="post" action="./gowithwriteaction.with" onsubmit="return writecheck();">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3"><h4>게시물 등록 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 130px; vertical-align: middle;"><h5>아이디</h5></td>
						<td style="text-align:center;">
						<img src="${requestScope.profilephoto}" width="200" height="200">
						<h5>${requestScope.user.userID }</h5> 
						<input
							type="hidden" name="userid" value="${requestScope.user.userID }"></td>
					</tr>
					<tr>
						<td style="width: 130px; vertical-align: middle;"><h5>글
								제목</h5></td>
						<td><input class="form-control" type="text" maxlength="50"
							id="title" name="title" placeholder="글제목을 입력하세요"></td>
					</tr>
					<tr>
						<td style="width: 130px; vertical-align: middle;"><h5>글
								내용</h5></td>
						<td><textarea class="form-control" rows="10"
								id="content" name="content" maxlength="2048" placeholder="여행에 관한 간단한 설명을 적어주세요"></textarea>
						</td>
					</tr>
					<tr>
						<td style="width: 130px; vertical-align: middle;" rowspan="2">
							<h5>여행정보선택</h5>
						</td>
						<td align="left">
							-기간 
							<input type="text" id="depart" name="date1" placeholder="depart date">~<input type="text" id="return" name="date2" placeholder="return date">
							<br/>
							-인원 
							<input type="radio" name="mem" value="2"> 2명 
							<input type="radio" name="mem" value="34"> 3~4명 
							<input type="radio" name="mem" value="58"> 5~8명 
							<input type="radio" name="mem" value="8"> 8명 이상 
							<input type="radio" name="mem" value="100"> 선택안함
						</td>
					</tr>
						<td align="left">
							-여행지 <input type="text" id="destination" name="destination" >
							-연령대 
							<input type="radio" name="age" value="10"> 10대 
							<input type="radio" name="age" value="20"> 20대 
							<input type="radio" name="age" value="30"> 30대 
							<input type="radio" name="age" value="40"> 40대 
							<input type="radio" name="age" value="0"> 선택안함
						</td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="3">
							<input class="btn btn-success pull-right" type="submit" value="등록">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>



	<c:if test="${ sessionScope.messageContent != null }">

		<div class="modal fade" id="messageModal" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="vertical-alignment-helper">
				<div class="modal-dialog vertical-align-center">
					<div class="modal-content">
						<div class="modal-header panel-heading">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" style="text-align: left;">
								${sessionScope.messageType}</h4>
						</div>
						<div class="modal-body">${sessionScope.messageContent}</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-success"
								data-dismiss="modal">확인</button>
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

	<c:if test="${ sessionScope.userID != null }">

		<script type="text/javascript">
			$(document).ready(function() {
				getUnread();
				getInfiniteUnread();
			});
		</script>
	</c:if>

	<script type="text/javascript">
		$(document).on('click', '.browse', function() {
			var file = $(this).parent().parent().parent().find('.file');
			file.trigger('click');
		});
		$(document).on(
				'change',
				'.file',
				function() {
					$(this).parent().find('.form-control').val(
							$(this).val().replace(/C:\\fakepath\\/i, ''));
				});
	</script>

	<jsp:include page="footer.jsp"/>

</body>
</html>