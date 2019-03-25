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
	
	
	$(function(){
		
		  var defalultTop = parseInt($("#quick_menu").css("top")); 

		  $(window).on("scroll",function(){
			  var scv = $(window).scrollTop();
			  $("#quick_menu").stop().animate({top:scv+defalultTop+"px"}  ,1000);
		});
	});	
</script>
</head>
<style>
.figure {
    left: 57px;
    margin-top: -80.42px;
    width: 686px;
    height: 698px;
    background: url(./bg/figure1.png) no-repeat;
    background-size: 686px 698px;
}
.figure2 {
    left: 57px;
    margin-top: -80.42px;
    width: 686px;
    height: 698px;
    background: url(./bg/figure2.png) no-repeat;
    background-size: 686px 698px;
}

.figure3 {
    left: 57px;
    margin-top: -80.42px;
    width: 686px;
    height: 698px;
    background: url(./bg/figure3.png) no-repeat;
    background-size: 686px 698px;
}

</style>

<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">

	<nav class="navbar navbar-default" style="margin-bottom:0;">
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
				<li class="active"><a href="index.user">메인</a></li>
				<li><a href="step1.trip">여행하기</a></li>
				<li><a href="boardView.board">관광지추천</a></li>
				<li><a href="boardView.myboard">자유게시판</a></li>
				<li><a href="gowithView.with">동행구하기</a></li>
				<li><a href="box.chat">메시지함 <span id="unread" class="label label-info"></span></a></li>

			</ul>
			<c:choose>
			<c:when test="${ sessionScope.userID == null }" >
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown">접속하기<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="login.user">로그인</a> </li>
						<li><a href="join.user">회원가입</a> </li>
					</ul>
				</li>
			</ul>
				</c:when>
				<c:otherwise>
				<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown">회원관리<span class="caret"></span>
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
				</c:otherwise>
			</c:choose>
		</div>
	</nav>
	
    	<div id="quick_menu">
			<a href="#">
				<img src="./bg/up.PNG" alt="상단으로 이동" />
			</a>
		</div>
	
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox" height="700px">
      <div class="item active imgh">
        <img src="./bg/1.jpg"  width="100%" height="700px">
        <div class="carousel-caption">
          <h3>Go</h3>
          <p> “Life is short and the world is wide”</p>
        </div>      
      </div>

      <div class="item imgh">
        <img src="./bg/7.jpg"  width="100%" height="700px">
        <div class="carousel-caption">
          <h3>Enjoy</h3>
          <p>”Adventure may hurt you but monotony will kill you.”</p>
        </div>      
      </div>
    
      <div class="item imgh">
        <img src="./bg/3.jpg"  width="100%" height="700px">
        <div class="carousel-caption">
          <h3>Far away</h3>
          <p>"Man cannot discover new oceans unless he has the courage to lose sight of the shore"</p>
        </div>      
      </div>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev" style="z-index: 1;">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next" style="z-index: 1;">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
</div>


<div class="frame">
 	<div class="row">
    	<div class="col-sm-6" style="padding:2rem; margin-top:8rem; vertical-align:center;">
     		<img src="./bg/STEP3.PNG" width="800px" height="600px"  > 
    	</div>
   	 	<div class="col-sm-6" style="line-height:normal; padding:2rem; margin-top:18rem; vertical-align:center; color:white; ">
   	 		<div class="figure">
   	 	     	 <br><br><br><br>
     		<h1>여행하기</h1><br>
     		<h2> 쉽고 빠르게 자신의 여행 일정 세우기 </h2><br>
     		<h4 style="line-height: 2;">
     		&nbsp;&nbsp;일정 계획 3단계와 옵션으로 숙박 검색과 항공권 검색을 지원하고 있습니다.<br>
     		추천 관광지를 드래그를 이용하여 원하는 일정에 넣으실 수 있습니다.<br>
     		일정 계획 3단계 후 자신이 계획한 일정을 확인 하실 수 있습니다.<br> 
     		또한, 여행계획보기에서 자신의 여행계획을 확인 하실 수 있습니다.<br>
     		(숙박과 항공권은 검색만 지원합니다.)
   			</h4>
   			</div>
   		</div>
	</div>
</div>

<div class="frame">
	<div class="row">
		<div class="col-sm-6" style="line-height:normal; padding:0rem 2rem 2rem 2rem; margin-top:18rem; vertical-align:center;color:white; ">
			<div class="figure2">
     			<br><br><br><br>
     		<div style="padding-right: 85px; padding-top:10px;">
     			<h1>1대1 채팅</h1><br>
     			<h2>채팅 기능을 이용하여  실시간 대화 가능</h2><br>
     			<h4 style="line-height: 2;">
     				게시판에서 작성자 아이디나 동행구하기의 메세지  <br> 
     				버튼을 클릭 하시면 메세지를 보내 실수 있습니다.<br> 
     				만약 아이디를 알고 있다면, 친구찾기에서<br>
     				 검색을 통해 메세지를 보내실수 있습니다.<br>
     				(상단의 메시지함에 실시간으로  읽지 않은 <br>
     				메세지의 수가 자동 업데이트)
   				</h4>
   			</div>
    	</div>
    </div>
		<div class="col-sm-6" style="padding:2rem; margin-top:5rem; vertical-align:center;">
        	<img src="./bg/chat.PNG" width="800px" height="700px"  > 
   		</div>
	</div>
</div>

<div class="frame">
 	<div class="row">
    	<div class="col-sm-6" style="padding:2rem; margin-top:5rem; vertical-align:center;">
     		<img src="./bg/my.jpg" width="700px" height="800px"  > 
    	</div>
   	 	<div class="col-sm-6" style="line-height:normal; padding:2rem; margin-top:20rem; vertical-align:center; color:white;">
   	 		<div class="figure3">
   	 			<br><br><br><br><br><br><br><br>
     			<h1>마이페이지</h1><br>
     			<h2>기억하고 싶은 나의 여행 스토리를 저장하고 공유하기</h2><br>
     			<h4 style="line-height: 2;">
				자신의 글을 저장하고 다른 사람과 공유하실수 있습니다. <br>
				자유게시판에 작성한 자신의 글들은 마이페이지에서도 확인 하실수 있습니다.<br>
				마이페이지에서 가장 최근에 등록한 동행구하기를 확인 하실수 있습니다.<br>
				게시글을 클릭하여 편리하게 등록한 글을 확인 할 수 있습니다.
   				</h4>
   			</div>
		</div>
	</div>
</div>


<div class="frame">
 	<div class="row">
    	<div class="col-sm-6" style="line-height:normal; padding:2rem; margin-top:18rem; vertical-align:center;color:white; ">
    	   	 <div class="figure">
    	   	 	<br><br><br><br>
    	     		<div style="padding-left: 30px; padding-top:10px;">
     					<h1>동행구하기</h1><br>
     					<h2>즐거운 여행을 위해 함께 여행할 동행 구하기</h2><br>
     					<h4 style="line-height: 2;">
     					혼자만의 여행이 지루하거나 두려우신가요,<br>
						새로운만남을 통해 보다 신선하고 색다른 여행을 하고싶으신가요,<br>
						단지 어딘가 가고 먹고 느끼는것만이 아닌<br>
						서로의 경험과 삶속까지 여행할수있는 기회를 동행구하기 게시판을 통해 찾아보세요
   						</h4>
   					</div>
   			</div>
    	</div>
   	 	<div class="col-sm-6" style="padding:2rem; margin-top:5rem; vertical-align:center;">
        	<img src="./bg/gowith.png" width="800px" height="500px"  > 
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
				 				<button type="button" class="btn btn-Success" data-dismiss="modal">확인</button>
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