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
	function registerCheckFunction(){
		var userID = $('#userID').val();
		$.ajax({
			type: 'POST',
			url	: './UserRegisterCheck.ajax',
			data: {userID: userID},
			success: function(result){
				if(result == 1){
					$('#checkMessage').html('사용할 수 있는 아이디입니다.');
					$('#checkType').attr('class', 'modal-content panel-success');
				}
				else {
					$('#checkMessage').html('사용할 수 없는 아이디입니다.');
					$('#checkType').attr('class', 'modal-content panel-warning');
				}
				$('#checkModal').modal("show");
			}
		});
	}
	
	function passwordCheckFunction(){
		var userPassword1 = $('#userPassword1').val();
		var password_pattern = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z]).*$/;
		if(password_pattern.test(userPassword1)){
			$('#passwordCheckMessage').html('사용 가능한 비밀번호입니다. 비밀번호 확인 부탁드립니다.');
			$('#passwordcheck').css("display", "");
		}else{
			$('#passwordCheckMessage').html('8자리 이상 15 자리 이하의 숫자 하나 이상을 포함한 비밀번호를 입력바랍니다.');
			$('#passwordcheck').css("display", "none");
		}
	}
	
	function passwordCheckFunction2(){
		var userPassword1 = $('#userPassword1').val();
		var userPassword2 = $('#userPassword2').val();

			if(userPassword1 != userPassword2){
				$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
			}else{
				$('#passwordCheckMessage').html('비밀번호가 체크 완료');
			}
	}
	
	function numberCheckFunction(){
		var userAge = $('#userAge').val();
		var userAge_pattern = /^.*(?=.*\d).*$/;// 나이는 숫자만 입력 가능

			if(userAge_pattern.test(userAge)){
				$('#passwordCheckMessage').html('');
			}else{
				$('#passwordCheckMessage').html('나이는 숫자만 입력 가능합니다');
			}
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
				<li><a href="find.chat">친구찾기</a></li>
				<li><a href="boardView.board">관광지추천</a></li>
				<li><a href="boardView.myboard">자유게시판</a></li>
				<li><a href="gowithView.with">동행구하기</a></li>				
				<li><a href="box.chat">메시지함 <span id="unread" class="label label-info"></span></a></li>
			</ul>
		
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="login.user">로그인</a> </li>
						<li class="active"><a href="join.user">회원가입</a> </li>
					</ul>
					
				</li>
			</ul>
		
		</div>
	</nav>
	
		<div class="container" id="body">
		<form method="post" action="./joinAction.user">
			<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3"><h4>회원 등록 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>아이디</h5></td>
						<td><input class="form-control" type="text" id="userID" name="userID" maxLength="15" placeholder="아이디를 입력해주세요"></td>
						<td style="width:130px;">
							<button class="btn btn-success" onclick="registerCheckFunction();" type="button" >중복체크</button>
						</td>
					</tr>
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>비밀번호</h5></td>
						<td colspan="2"><input class="form-control" type="password" onkeyup="passwordCheckFunction();" id="userPassword1" name="userPassword1" maxLength="15"  placeholder="비밀번호를 입력해주세요"></td>
					</tr>				
					<tr  id="passwordcheck"  Style="display:none;" >
						<td style="width:130px; vertical-align: middle;"><h5>비밀번호 확인</h5></td>
						<td colspan="2"><input class="form-control" type="password" onkeyup="passwordCheckFunction2();" id="userPassword2" name="userPassword2" maxLength="15"  placeholder="비밀번호 확인을 입력해주세요"></td>
					</tr>					
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>이름</h5></td>
						<td colspan="2"><input class="form-control" type="text" id="userName" name="userName" maxLength="20"  placeholder="이름을 입력해주세요"></td>
					</tr>	
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>나이</h5></td>
						<td colspan="2"><input class="form-control" type="text" onkeyup="numberCheckFunction();" id="userAge" name="userAge" maxLength="2"  placeholder="나이를 입력해주세요"></td>
					</tr>
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>성별</h5></td>
						<td colspan="2">
							<div class="form-group"  style="text-align: center; margin:0 auto; ">
								<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-success active">
									<input type="radio" name="userGender" autocomplete="off" value="남자" checked>남자
								</label> 
								<label class="btn btn-success">
									<input type="radio" name="userGender" autocomplete="off" value="여자">여자
								</label> 
								</div>
							</div>
						</td>
					</tr> 
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>이메일</h5></td>
						<td colspan="2"><input class="form-control" type="email" id="userEmail" name="userEmail" maxLength="20"  placeholder="이메일을 입력해주세요"></td>
					</tr>
					<tr>
						<td style="width:130px; vertical-align: middle;"><h5>좌우명</h5></td>
						<td colspan="2">
							<textarea class="form-control" rows="4" name="userMotto" maxlength="512"  placeholder="자신의 좌우명 또는 자신을 소개하는 글을 입력하세요"></textarea> 
						</td>
					</tr>				
					
					
					<tr>
						<td style="text-align: right;" colspan="3">
							<h5 style="color: red; text-align: left;" id="passwordCheckMessage"></h5>
							<input class="btn btn-success pull-right" type="submit" value="회원가입">
						</td>
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

		<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
				 <div class="vertical-alignment-helper"> 
				 	<div class="modal-dialog vertical-align-center">
				 		<div id="checkType" class="modal-content panel-info">
				 			<div class="modal-header panel-heading">
				 				<button type="button" class="close" data-dismiss="modal">
				 					<span aria-hidden="true">&times;</span>
				 					<span class="sr-only">Close</span>
				 				</button>
				 				<h4 class="modal-title" style="text-align: left;">
				 					확인 메시지
				 				</h4>
				 			</div>
				 			<div class="modal-body" id="checkMessage">
				 			</div>
				 			<div class="modal-footer">
				 				<button type="button" class="btn btn-success" data-dismiss="modal">확인</button>
				 			</div>
				 		</div>
				 	</div>
				 </div>
			</div>
	
	<jsp:include page="footer.jsp"/>
</body>
</html>