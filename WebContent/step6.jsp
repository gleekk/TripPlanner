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

	function citycodefindFunction1() {
		var city = $('#scity1').val();
		$.ajax({
			type : 'POST',
			url : './CityCodeFind.ajax',
			data : {
				city : city
			},
			success : function(result) {
				if (result == -1) {
				} else if (result == 1) {
				} else {
					var data = JSON.parse(result);
					var city = data.cityName; // 검색중인 도시 
					var cityCode = data.cityCode; // 검색중인 도시 공항코드
					$('#scity12').val(cityCode);
					$('#scity1').append(cityCode);
				}
			}
		});
	}

	function citycodefindFunction2() {
		var city = $('#scity2').val();
		$.ajax({
			type : 'POST',
			url : './CityCodeFind.ajax',
			data : {
				city : city
			},
			success : function(result) {
				if (result == -1) {
				} else if (result == 1) {
				} else {
					var data = JSON.parse(result);
					var city = data.cityName; // 검색중인 도시 
					var cityCode = data.cityCode; // 검색중인 도시 공항코드
					$('#scity22').val(cityCode);
					$('#scity2').append(cityCode);
				}
			}
		});
	}

	function AirSearchFunction() {
		//	var way = $('#keyword').val();
		var scity1 = $('#scity12').val();
		var scity2 = $('#scity22').val();
		//	var adult = $('#adult').val();
		var sdate1 = $('#sdate1').val();
		var sdate2 = $('#sdate2').val();
		var people = $('#people').val();

		var url = 'https://store.naver.com/flights/v2/results?trip=RT&scity1='
				+ scity1 + '&ecity1=' + scity2 + '&scity2=' + scity2
				+ '&ecity2=' + scity1 + '&adult=' + people
				+ '&child=0&infant=0&sdate1=' + sdate1 + '&sdate2=' + sdate2
				+ '&fareType=Y';

		//alert(url);
		//window.open(url);	
		$('#box').attr('src', url);

	}
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
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
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
			<li class="dropdown"><a href="#" class="dropdown-toggle"
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
						
				</ul></li>
		</ul>
	</div>
	</nav>
	
		<div id="body">
	

	<section class="container" style="text-align:center;">
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4 info text-left">
			<h1 Style="font-size: 3em; text-align: center;">${sessionScope.userID}님의
				<br>항공권 예매
			</h1>
		</div>
		<div class="col-md-4"></div>
	</div>
	<a href="./step4.trip"
		class="btn btn-success btn-lg pull-right pull-bottom" role="button">전체계획보기</a>
	<a href="step5.jsp"
		class="btn btn-success btn-lg pull-right pull-bottom" role="button">숙박예매</a>


	</section>
	<hr>


	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">

			<div class="form-inline" Style="margin: 1em; text-align: center;">
				<div class="form-group" Style="margin: 1em;">
					<label for="exampleInputEmail3">출발</label> 
					<input type="text" name="departure" id="scity1" class="form-control input-lg"
						onkeyup="citycodefindFunction1();">
						<input type="hidden" id="scity12" value="">
					<span class="icon-flight-1" aria-hidden="true" style="display: inline; font-size: 3em; margin-left: 1em"></span>
				</div>

				<div class="form-group" Style="margin: 1em;">
					<label for="exampleInputPassword3">도착</label> <input type="text"
						name="arrival" id="scity2" class="form-control input-lg"
						onkeyup="citycodefindFunction2();"> <input type="hidden"
						id="scity22" value="">
				</div>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>


	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<div class="form-inline" Style="margin: 2em; text-align: center;">
				<div class="form-group" Style="margin: 1em;">
					<label for="exampleInputEmail3">출발일</label> <input
						class="form-control input-lg" type="date" name="departure_date"
						id="sdate1">
				</div>
				<div class="form-group" Style="margin: 1em;">
					<label for="exampleInputPassword3">귀국일</label> <input
						class="form-control input-lg" type="date" name="arrival_date"
						id="sdate2">
				</div>
			</div>
			<div class="form-inline" Style="margin: 1em; text-align: center;">
				<select style="display: inline; margin: 1em; font-size: 1.3em;"
					name="people" id="people" class="form-control input-lg">
					<option value="1">1인</option>
					<option value="2">2인</option>
					<option value="3">3인</option>
					<option value="4">4인</option>
				</select>
				<button class="btn btn-success btn-lg"
					onclick="AirSearchFunction();" type="button" data-toggle="collapse"
					data-target="#demo3">항공권 검색</button>
			</div>
		</div>
		<div class="col-md-3"></div>
	</div>

	<p align="middle" id="demo3" class="collapse">
		<iframe src="#" width="1200" height="1500" name="boxhtm" id="box"
			style="margin: auto;"></iframe>
	</p>

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