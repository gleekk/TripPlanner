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

	function cityfindFunction() {
		var city = $('#city').val();
		$.ajax({
			type : 'POST',
			url : './CityFind.ajax',
			data : {city : city },
			success : function(result) {
				if (result == -1) {
					$('#cityResult').empty("검색 결과가 없습니다.");
					$('#cityRecommend').show();
					failCity();
										
				}else if (result == 1) {
					$('#checkMessage').html('검색어가 없습니다.');
					$('#checkType').attr('class', 'modal-content panel-warning');
					failCity();
					$('#cityRecommend').show();
				} 
				else {
					var data = JSON.parse(result);
					var city = data.cityName;					
					getCity(city);
					$('#cityRecommend').hide();
				}
			}
		});
	}

	function getCity(city) {

		$('#cityResult')
				.html(
						'<thead>'
								+ '<tr>'
								+ '<th><h4>검색 결과</h4></th>'
								+ '</tr>'
								+ '</thead>'
								+ '<tbody>'
								+ '<tr>'
								+ '<td style="text-align: center;">'
								+ '<img  style="width:900px; margin: 0 auto;" src="images/'+city+'.jpg">'
								+ '<h3>' + city
								+ '</h3><a href="step2.trip?city='
								+ encodeURIComponent(city)
								+ '" class="btn btn-success pull-right">'
								+ city + '여행하기</a></td>' + '</tr>' + '</tbody>');

	}
	function failCity() {
		$('#cityResult').html("");
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
	<!--여행지 검색 부분 -->
	<section class="container" style="text-align:center;">
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8 info text-left">
			<h1 Style="font-size: 3em; text-align: center;">${sessionScope.userID}님
				<br>어디로 떠나시나요?
			</h1>
			
			<div class="ui-widget">
			<div class="col-md-3"></div>
			<div class="col-md-6">
			
			<input type="text" id="city" size="5" Style="font-size: 1.3em; height: 2em; display:inline;"
				class="form-control mx-1 mt-2" placeholder="여행할 도시를 입력하세요." onkeyup="cityfindFunction();">
					</div>	<div class="col-md-3"></div>
			
			
			</div>
			
		</div>
		<div class="col-md-2" Style=" float:bottom; margin-top: 120px;">
<!-- <button onclick="cityfindFunction();" class="btn btn-success mx-1 mt-2" Style="font-size: 1.3em; float:left;">도시 검색</button>-->		
		</div>
	</div>
	</section><!--여행지 검색 부분 끝 -->
	
	<hr>
	
	<!--추천 여행지 부분 -->
	<div id="cityRecommend" class="row">
	
		<div class="col-md-2"></div>
		<div class="col-md-8" style="text-align: center;">
				<p class="cityRecomendTitle">추천 여행지</p>
		
			<c:forEach var="city" items="${requestScope.cityList}" end="4" varStatus="status">
				<div style="text-align: center; display:inline-block;">
				<a href="step2.trip?city=${city.city}" >
				<img class="media-object" width="200" height="150" border="1" src="images/${city.city}.jpg">
				 ${city.city}여행하기</a>
				</div>
			</c:forEach>
				
		
		</div>
		<div class="col-md-2">
		</div>
	</div>
	
	
	<!--  검색한 여행지  -->

	<div class="container">
		<table id="cityResult" class="table table-bordered table-hover"
			style="text-align: center; border: 1px solid #dddddd">
		

		</table>
		
		
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

	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id="checkType" class="modal-content panel-info">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="text-align: left;">확인 메시지</h4>
					</div>
					<div class="modal-body" id="checkMessage"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>

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