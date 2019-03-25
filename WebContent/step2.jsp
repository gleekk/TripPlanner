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
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<link href="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>Trip_Planner</title>
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

	var from = new Date();
	var to = new Date();
	var dayDiff = 1;
	var	minDate = new Date();
	
	$(function() {
	var dates = $( "#departDay, #returnDay" ).datepicker({
	    defaultDate: "+1w",
	    changeMonth: true,
	    minDate: minDate,
	    dateFormat:'yy-mm-dd',
	        numberOfMonths: 1,
	        onSelect: function( selectedDate ) {
	            var option = this.id == "departDay" ? "minDate" : "maxDate",
	                instance = $( this ).data( "datepicker" ),
	                date = $.datepicker.parseDate(
	                    instance.settings.dateFormat ||
	                    $.datepicker._defaults.dateFormat,
	                    selectedDate, instance.settings );
	            dates.not( this ).datepicker( "option", option, date );

	            if (this.id == "departDay"){
	                from = $(this).datepicker('getDate');
	                if (!(to == "")){
	                    update_days()
	                }
	            }
	            if (this.id == "returnDay"){
	                to = $(this).datepicker('getDate');
	                update_days()
	            }
	        }
	    });
	});

	function update_days(){
	    dayDiff = Math.ceil((to - from) / (1000 * 60 * 60 * 24));
	    dayDiff2 = Math.ceil((to - from) / (1000 * 60 * 60 * 24))+1;

	   $('#nofday').attr('value', dayDiff2 );
	}	
	
});

	
	
</script>
</head>
<style>

.weather tr {
    width: 100%;
    height: 100px;
    text-align: center;
}
.weather tr td {
    width: 250px;
    border: 1px solid lightgray;
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
			<h1 Style="font-size: 3em; text-align: center;">${sessionScope.userID}님 ${requestScope.city}여행
				<br>언제 떠나시나요 ?
			</h1>
		</div>
		<div class="col-md-2"></div>
	</div>
	

	</section>
	<hr>

	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">
			
		<form action="step3.trip" method="post">
			<input type="hidden" name="city" id="city" value="${requestScope.city}">
			<input type="hidden" name="nofday" id="nofday" value="">
		
		
			<label>출발일
				<input type="text" name="departDay" id="departDay" placeholder="depart date">
			</label>
			<label>귀국일
				<input type="text" name="returnDay" id="returnDay" placeholder="return date">
			</label>	
				<input type="submit" value="떠나요">
		</form>



		</div>
		<div class="col-md-3">
		</div>
	</div>


	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
		<div class="wrap">
		<p class="WeatherTitle">${requestScope.city} 날씨정보</p>
			<table class="weather">
				<tr>	
				
		<c:forEach var="weather" items="${requestScope.weatherlist}" varStatus="status">
			
					<td>
						<p class="font_head">${weather.days}</p><br>
						<p class="font_m"><img src="${weather.imgs}"></p>
						<p class="font_m">${weather.weathers}</p>
						<p class="font_m">최저기온 : ${weather.lows}</p>
						<p class="font_m">최고기온 : ${weather.highs}</p>
					</td>
			</c:forEach>	
			</tr>
		</table>
		<!-- 파싱  끝 -->
	</div><!-- wrap -->
		</div>
		<div class="col-md-3">
		</div>
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