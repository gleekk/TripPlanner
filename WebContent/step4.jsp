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
<link href='./css/fullcalendar.css' rel='stylesheet' />
<link href='./css/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<title>Trip_Planner</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src='./js/moment.min.js'></script>
<script src='./js/fullcalendar.js'></script>
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
		}, 9000);
	}
	function showUnread(result) {
		$('#unread').html(result);
	}
	

 	 
		$(document).ready(function(){ // 계획 리스트 불러오기 함수 
	
	  // 세부 계획 불러 오기 함수
		 var planNo= "";
		 $(".planNumner").on("click",function(){
			 var planNo= $(this).attr('value');
			 var city= $(this).attr('id');
			 $("#tit").text("계획 번호 "+planNo+"의 관광지 일정");
			 $("#tit2").html('<h5>'+city+' 관광지</h5>');
			 
			  $.ajax({
					type : 'POST',
					url : './FindSchedule.ajax',
					data : {
					planNo : planNo},
					success : function(data) {
						if (data == "") {
						$('#insertSchedule').empty();
						$('#insertSchedule').html("<th colspan='5' style='text-align:center;'><h4>세부 일정이 없습니다.</h4></th>");
						}else{
							$('#insertSchedule').empty();
						var parsed = JSON.parse(data);
						var result = parsed.result;
						for(var i = 0; i< result.length; i++){
						addSchedule(result[i][0].value, result[i][1].value, 
								result[i][2].value, result[i][3].value);
							}
						}
						}
					});	
			  $.ajax({
					 type:'POST',
					 url : './CalAddValServlet',
					data :{
						planNo : planNo	},				 			 
				  success : function(data){
					  var put = JSON.parse(data); 
					 
					  if($("#here").hasClass("fc fc-unthemed fc-ltr") ==true){
						  $("#here").fullCalendar( 'destroy' );	 
					  $('#here').fullCalendar('renderEvent', put);
					 
					  }   				  
					  $("#here").fullCalendar({ 		  
	 							 
				  header: {
				        left: 'prev,next today', //이전달, 다음달, 오늘로 이동하는 기능
				        center: 'title', //현재 출력된 월에 대한 정보 출력
				        right: 'month,basicWeek,basicDay' // 날짜를 보는 기간(월,주,일)
				      },
				      defaultDate: new Date(), //처음 실행되면서 보여줄 일자를 의미한다
				      navLinks: true, // 월/주별 달력에서 일자를 클릭하면 일별보기로 전환하는 기능을 사용할지에 대한 여부 결정
				      editable: false, // 실행된 달력에서 일정(event)를 표시한 바를 마우스로 이동할 수 있게 하는 것, 일정 끝길이 변경
				      buttonText: { // 버튼 한글로
				    	   today : "오늘",
				    	   month : "월별",
				    	   week : "주별",
				    	   day : "일별",
				    	   },  
					  views: {
						  week : {titleFormat : 'YYYY년 MMMM DDD일'},
						  day : {titleFormat : 'YYYY년 MMMM DD일 dddd'}
							  },
							  startEditable : false, // 일정 시작길이 변경
							  
							  eventResourceEditable: true, //일정 내용수정
							  seclectable : true,
				      monthNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
				      monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
				      dayNames: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"],
				      dayNamesShort: ["일","월","화","수","목","금","토"],
				      resourceEditable: true,
				      eventLimit: true, // 하루에 일정을 3개만 보여주고 그 이상은 more로 처리할지 여부
						    
				       events : [{
					            title : put.title,
					            start : put.start,
					            end : put.end 		            
					  }]   				  
				  }); // fullcal 끝
				  
	     				} // success : function 끝
				  }); //ajax끝
			 });  // on("click",) 함수끝
				
			function addSchedule(planNo, dayNo, thingNo, touristName){
				$('#insertSchedule').append(
							'<tr>' +
							'<td>' + planNo +'</td>' +
							'<td>' + dayNo +'</td>' +
							'<td>' + thingNo +'</td>' +
							'<td>' + touristName +'</td>' +
							'</tr>' +
							'<hr>');
				}
		});
 
</script>
</head>
<style>
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
				<br>여행 계획 전체 보기
			</h1>
		</div>
		<div class="col-md-2"></div>
	</div>
	<div>
		<form action="# <!-- search.trip --> " method="get"
			class="form-inline mt-3">
			<!-- ajaxs 결과 뿌려주기  -->
		</form>
	</div>
	<a href="step6.jsp"
		class="btn btn-success btn-lg pull-right pull-bottom" role="button">항공권예매</a>
	<a href="step5.jsp"
		class="btn btn-success btn-lg pull-right pull-bottom" role="button">숙박예매</a>

	</section>
	<hr>


	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="7"><h4>여행 계획</h4></th>
					</tr>
					<tr>
						<th style="background-color: #fafafa; color: #000000;"><h5>계획번호</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>도시</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>출발일</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>귀국일</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>기간</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>삭제</h5></th>

					</tr>
				</thead>
				<tbody id="insertPlan">
				
					<c:forEach var="plan" items="${requestScope.planList}" varStatus="status">
						<tr>
						<td><button class="planNumner" id="${plan.city}" value="${plan.planNo}">${plan.planNo}</button></td>
						<td> ${plan.city}</td>
						<td> ${plan.departDay}</td>
						<td> ${plan.returnDay}</td>
						<td> ${plan.nofday-1}박/${plan.nofday}일</td>
						<td><a href="./stepDel.trip?planNo=${plan.planNo}" class="btn">삭제</a>			
<a href="./step3up.trip?planNo=${plan.planNo}&city=${plan.city}&departDay=${plan.departDay}&returnDay=${plan.returnDay}&nofday=${plan.nofday}" class="btn">수정</a>
						</td>
						</tr>
					</c:forEach>
	
				</tbody>
			</table>

		</div>
		<div class="col-md-3"></div>
	</div>


	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">

			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="4"><h4>
							<span id="tit">관광지 일정</span>
							</h4></th>
					</tr>
					<!-- <tr><td colspan="4" id="here"></td></tr> -->
					<tr>
						<th style="background-color: #fafafa; color: #000000;"><h5>계획번호</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>날짜</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5>일정번호</h5></th>
						<th style="background-color: #fafafa; color: #000000;"><h5><span id="tit2">관광지</span></h5></th>
					</tr>
				</thead>
				<tbody id="insertSchedule">



				</tbody>
				<tr><td colspan="4" id="here"></td></tr>
			</table>


		</div>
		<div class="col-md-3"></div>
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
				/* planlistFunction(); */
			});
		</script>
	</c:if>
	<jsp:include page="footer.jsp"/>
</body>
</html>