<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage = "errorPage.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/custom.css">
<title>Trip_Planner</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="./js/bootstrap.min.js"></script>
<script type="text/javascript">
	function getUnread() {
		$.ajax({
			type : 'POST',
			url : './ChatUnread.ajax',
			data : {
				userID : encodeURIComponent('${sessionScope.userID}'),
			},
			success : function(result) {
				if (result >= 1) {
					showUnread(result);
				} else {
					showUnread('');
				}
			}
		});
	}
	function getInfiniteUnread() {
		setInterval(function() {
			getUnread();
		}, 4000);
	}
	function showUnread(result) {
		$('#unread').html(result);
	}

	
	$(document).ready(function(){

		  $("#click").click(function(){   // 여행 일정 저장
		    var html = encodeURIComponent($("#tabs").html())
			  $.ajax({
					type : 'POST',
					url : './ScheduleSave.ajax',
					data : {
					note : html,
					departDay : '${requestScope.departDay}'
					}
				  });
			  });
		  }); 
	
	   $(document).ready(function(){
		   
		   for(var i = 1; i < '${requestScope.nofday+1}' ;i++) {
				$('.DayNum').append(
						'<li><a href="#tabs-'+i+'">DAY'+i+'</a></li>');
				
				$('.NoteFrame').append(
						'<div id="tabs-'+i+'">'+
							'<div class="trip_note'+i+'" id="yellonote">관광지를 넣어주세요 (drag in to here)<br>'+
								'<ol id=notecontent'+i+'></ol>'+
							'</div>'+
						'</div>');
				}
				$("#tabs").tabs({collapsible : true});
		   
		   
		      $(".checks b").draggable({helper:"clone"});

		      $(".trip_note1").droppable({drop:function(evnet,ui){
		      $("#notecontent1").append($("<li class='view1' id='view1'></li>").html(ui.draggable.html()));
			  $(".view1").click(function(){$(this).remove();});}});
		      
		      $(".trip_note2").droppable({drop:function(evnet,ui){
			  $("#notecontent2").append($("<li class='view2' id='view2'></li>").html(ui.draggable.html()));
			  $(".view2").click(function(){$(this).remove();});}});
		          
		      $(".trip_note3").droppable({drop:function(evnet,ui){
			  $("#notecontent3").append($("<li class='view3' id='view3'></li>").html(ui.draggable.html()));
			  $(".view3").click(function(){$(this).remove();});}});
			      
			  $(".trip_note4").droppable({drop:function(evnet,ui){
			  $("#notecontent4").append($("<li class='view4' id='view4'></li>").html(ui.draggable.html()));
			  $(".view4").click(function(){$(this).remove();});}});
			  

		      $(".trip_note5").droppable({drop:function(evnet,ui){
			  $("#notecontent5").append($("<li class='view5' id='view5'></li>").html(ui.draggable.html()));
			  $(".view5").click(function(){$(this).remove();});}});
			      
			  $(".trip_note6").droppable({drop:function(evnet,ui){
			  $("#notecontent6").append($("<li class='view6' id='view6'></li>").html(ui.draggable.html()));
			  $(".view6").click(function(){$(this).remove();});}});
			          
			  $(".trip_note7").droppable({drop:function(evnet,ui){
			  $("#notecontent7").append($("<li class='view7' id='view7'></li>").html(ui.draggable.html()));
			  $(".view7").click(function(){$(this).remove();});}});
				      
			  $(".trip_note8").droppable({drop:function(evnet,ui){
			  $("#notecontent8").append($("<li class='view8' id='view8'></li>").html(ui.draggable.html()));
			  $(".view8").click(function(){$(this).remove();});}});	

			  $(".trip_note9").droppable({drop:function(evnet,ui){
			  $("#notecontent9").append($("<li class='view9' id='view9'></li>").html(ui.draggable.html()));
			  $(".view9").click(function(){$(this).remove();});}});
					      
			  $(".trip_note10").droppable({drop:function(evnet,ui){
			  $("#notecontent10").append($("<li class='view10' id='view10'></li>").html(ui.draggable.html()));
			  $(".view10").click(function(){$(this).remove();});}});	

	   });
	

</script>
</head>
<style>
.checks {
	position: relative;
}

#yellonote {
	background-color: #ffff80;
	width: 450px;
	min-height: 600px;
	display: block;
	padding: 0.5em;
	font-size: 1.5em;
	font-family : 'Hanna';
	z-index: 1;
	
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
				<li class="active"><a href="step1.trip">여행하기</a></li>
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

	<section class="container" style="text-align:center;">
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8 info text-left">
			<h1 Style="font-size: 3em; text-align: center;">${sessionScope.userID}님의
				${requestScope.city}여행 <br>관광지 일정 추가하기
			</h1>
		</div>
		<div class="col-md-2"></div>
	</div>
	</section>


	<hr>

	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-4">
			<div class="con"
				style="font-size: 2rem; background-color: rgba(255, 255, 255, 0.3); font: bold; color: black;">
				<h4>추천 관광지</h4>
				<div class="checks">
				<c:forEach var="tour" items="${requestScope.tourlist}" varStatus="status">
					${tour}
					
				</c:forEach>
				<h4>회원 추천 관광지</h4><ol>
			<c:choose>
				<c:when test="${requestScope.memtourlist.size()==0}" >
					회원 추천 관광지가 없습니다.
				</c:when>
				<c:otherwise>
					<c:forEach var="memtour" items="${requestScope.memtourlist}" step="1" varStatus="status">
						<li><b>${memtour.tourist}</b>
						<a href="boardShow.board?boardID=${memtour.boardID}" target="_blank" >자세히 보기</a></li>
					</c:forEach>				
				</c:otherwise>
			</c:choose>
			</ol>
				
				</div>
			</div>
		</div>
		<div class="col-md-5" style="width:550px;">
			<h2>일정 짜기</h2>
			<fieldset>
			
		<div id="tabs">
			<ul class="DayNum">
		
			</ul>	
					<div class="NoteFrame">
			
					</div>
				</div>
			</fieldset>
	<a href="./step4.trip" id="click" name="send" class="btn btn-success pull-right">작성완료</a>

		</div>

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
			<jsp:include page="footer.jsp"/>
</body>
</html>