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
<link rel="stylesheet" href="./fontello-32fe2350/css/fontello.css">
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
	
	
	function gowithinfo(no){
 		var no = no;
 		$.ajax({
 			type : 'POST',
 			url : './GowithInfo.ajax',
 			data : { no : no},
 			datatype : 'json',
 			success : function(data){
 				var getinfo = JSON.parse(data);
 				if(getinfo.userid==getinfo.user){
 					var no=getinfo.no;
 					$("#btn").css("display", "");
 					$("#btn").attr("onclick",  "gowithdelete("+no+")");

 				}else{
 					$("#btn").css("display", "none");	
 				}
 				$("#getinfo").html(
 				"<table style='width:560px'>"+
 					"<thead>"+
 						"<tr>"+
 							"<th colspan=3>"+
 								"<h4>게시물 정보</h4>"+
 							"</th>"+
 						"</tr>"+
 					"</thead>"+
 					"<tbody style='border: 1px solid #dddddd;'>"+
 						"<tr style=margin-left:10px;>"+
 							"<td style=text-align:left; margin-left:10px>"+
 							"<img src="+getinfo.profilephoto +" width=200 height=200>"+
 							"</td>"+
 							"<td align=left style='font-size:2rem;border: 1px solid #dddddd;'>- 작성자 : "+getinfo.userid +
 							"<br/>- 기간 : "+getinfo.date1+" ~ "+getinfo.date2+
 							"<br/>- 인원 : "+getinfo.mem +
 							"<br/>- 여행지 : "+getinfo.destination+
 							"<br/>- 연령대 : "+getinfo.age+"</td>"+
 						"</tr>"+
 						"<tr>"+
 							"<td colspan=2 style='font-size:2rem;background-color:#fafafa;height:4rem;border: 1px solid #dddddd;'>"
 								+getinfo.title+
 							"</td>"+
 						"</tr>"+
 						"<tr>"+
 							"<td colspan=2 style='font-size:2rem;height:200px; border: 1px solid #dddddd;'>"
 								+getinfo.content+
 							"</td>"+
 						"</tr>"+
 					"</tbody>"+
 				"</table>");
 			}
 			})
 		};	
 		function readBoard(no){
 			var no = no;
 	 		var myuserID = '${requestScope.userID}';

 			$.ajax({
 	 			type : 'POST',
 	 			url : './MyBoardView.ajax',
 	 			data : { no : no},
 	 			datatype : 'json',
 	 			success : function(data){
 	 				var board = JSON.parse(data);
 	 				if(board.userID==board.dbID){
 	 					
 	 					$("#delbtn").css("display", "");
 	 					$("#updatebtn").css("display","");
 	 					$("#delbtn").attr("href" , "boardDelete.myboard?myboardID="+board.no+"&pagename=mypage");	 					
 	 					$("#updatebtn").attr("href", "boardUpdate.myboard?myboardID="+board.no+"&pagename=mypage");

 	 				}else{
 	 					$("#updatebtn").css("display", "none");
 	 					$("#delbtn").css("display", "none");	
 	 				}	 				 	 				
 	 				$("#boardinfo").html("<table class=table table-bordered table-hover style=text-align:center; border: 1px solid #dddddd>"
 	 				+"<thead><tr><th colspan=6><h4>마이페이지 게시물 보기</h4></th></tr>"
 					+"<tr><td style='background-color:#fafafa; color:#000000; width:80px;'><h5>분류</h5></td>"
 					+"<td style=width:150px;><h5>"+board.category+"</h5> </td>"
 					+"<td style=background-color:#fafafa; color:#000000; width:80px;><h5>일정</h5></td>"
 					+"<td ><h5>"+board.plan+"</h5> </td>"
 					+"<td style=background-color:#fafafa; color:#000000; width:80px;><h5>작성자</h5></td>"
 					+"<td><h5>"+board.dbID+"</h5> </td></tr>"
 					+"<tr><td style=background-color:#fafafa; color:#000000; width:80px;><h5>제목</h5></td>"
 					+"<td colspan=3 ><h5>"+board.title+"</h5> </td>"
 					+"<td style=background-color:#fafafa; color:#000000; width:80px;>"
 					+"<a onclick=return confirm('추천하시겠습니까?') href='./likeAction.myboard?myboardID="+board.no+"&myuserID="+ myuserID +"&pagename=mypage'><h5>좋아요</h5></a></td>"
 					+"<td><h5>"+board.likecount+"</h5> </td></tr>"
 					+"<tr><td style=background-color:#fafafa; color:#000000; width:80px;><h5>작성날짜</h5></td>"
 					+"<td colspan=3 ><h5>"+board.date+"</h5> </td>"
 					+"<td style=background-color:#fafafa; color:#000000; width:80px;><h5>조회수</h5></td>"
 					+"<td><h5>"+board.hit+" </h5> </td></tr>"
 					+"<tr><td style=vertical-align: middle; min-height:400px; background-color:#fafafa; color:#000000; width:80px;><h5>글 내용</h5></td>"
 					+"<td colspan=5 style=text-align: left; height : 400px;><h5>"+board.content+"</h5> </td></tr>"				
 					+"</thead><tbody></tbody></table>"
 			);
 	 			}
 	 			})
 		};
	
	
</script>
</head>
<style>





.navbar{
	background-color: orange;
		background-size: cover;
	
}

.navbar:hover{
	color: #FF5400;
}

 body{
	background-color:white;
	background-size: cover;
		font-family: 'Source Sans Pro', sans-serif; 	
} 
#wrapper{
	max-width:90rem;
	margin: 0 auto;
}
#header {
	text-align: left;
	padding-top:2rem;
	padding-bottom: 2rem;	
}

#header .avatar{
	border-radius: 100%;
	display:inline-block;
	padding:0.5rem;
	background-color :rgba(255, 255, 255, 0.05);
	border:1px solid rgba(255, 255, 255, 0.25);
	margin-bottom: 1.5rem;
}


#header .avatar img{
	border-radius: 100%;
	display:block;
	width:30rem;	
	float: left;
}

#header ul.icons li{
	display: inline-block;
	border-radius: 100%;
	border: 1px solid rgba(255,255,255,0.25);
	background-color :#ff9d2d;
	width:5rem;
	height:5rem;
	display: inline-flex;
	justify-content: center;
	align-items: center;
	margin-right:1rem;	
}

#header ul.icons li:hover{
	border: #FF5400;
	background-color: #FF5400;
}

#header ul.icons li a{
	color:rgba(255,255,255,0.5);
	text-decoration:none;
	font-size: 2.8rem;
}


#header ul.icons li .label{
	display: none;
}

#contents{
margin-top:2rem;
display: inline;
width:50rem;
float: right;
font-size:3rem;
color:#8e0b0b;
font-weight: 200;
margin-bottom: 1.5rem;
line-height : 2.3rem;

}


#main .thumbnails {
	display: flex;
	/*  align-items: center;
 */
}

#main .thumbnails>div {
	flex: 1;
	margin-right: 1rem;
	margin-left: 1rem;
}


#main .thumbnails img {
	width: 100%;
	border-top-left-radius: 0.2rem;
	border-top-right-radius: 0.2rem;
	display: block;
	border: 1px solid rgba(0, 0, 0, 0.3);
}

#main .thumbnails h3 {
	border-left: 1px solid rgba(0, 0, 0, 0.3);
	border-right: 1px solid rgba(0, 0, 0, 0.3);
	border-bottom: 1px solid rgba(0, 0, 0, 0.3);
	border-bottom-left-radius: 0.2rem;
	border-bottom-right-radius: 0.2rem;
	padding: 0.5rem;
	text-align: center;
	background-color: rgba(0, 0, 0, 0.05);
	color: black;
	font-size: 1rem;
	font-weight: 200;
}

@media ( max-width : 480px) {
#main .thumbnails {
	display: block;
}
}
	.prev2, .next2 {
		cursor: pointer;
		position: absolute;
		top: 0;
		width: auto;
		padding: 16px;
		color: black;
		font-weight: bold;
		font-size: 100px;
		border-radius: 0 3px 3px 0;
		user-select: none;
		-webkit-user-select: none;
	}
	.next2 {
		right: 0;
		border-radius: 3px 0 0 3px;
	}
	.prev2:hover, .next2:hover {
		background-color: rgba(0, 0, 0, 0.3);
	}
.btn{
    margin-bottom: 2rem !important;
}	
	

</style>
<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">


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
				<li><a href="gowithView.with">동행구하기</a></li>			
				<li><a href="box.chat">메시지함 <span id="unread" class="label label-info"></span></a></li>
			</ul>
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
		</div>
	</nav>
	

	<div id="body">
	
	<div id="wrapper">
	<br>
	<div style="text-align: center; color:orange;">
	<h1>${requestScope.userID}님의 마이페이지 </h1>
	</div>
	<div class=row>

				<div class="col-md-5">
					<header id="header">
						<span class="avatar">
							<img src="${requestScope.profile}" alt="" /></span>
								<ul class="icons">
									<li><a href="#" class="twitter icon-twitter"><span class="label"></span></a></li>
									<li><a href="#" class="facebook icon-facebook"><span class="label"></span></a></li>
									<li><a href="#" class="instagram icon-instagram"><span class="label"></span></a></li>
									<li><a href="#" class="mail icon-mail"><span class="label"></span></a></li>
								</ul>
					</header>
				</div>
				<div class="col-md-7">
					<div id="contents"  style="height: 160px;"  >
						<h3>${requestScope.user.userMotto}</h3>
					</div>
		
		
		<c:if test="${requestScope.getlist.date1 != null }">
		
			<table style="text-align:center;  color:#FF5E00; ">
			<thead>
				<tr>
					<td colspan="5"><h3>&#10094; 동행 구하기 &#10095;</h3></td>
				</tr>
				<tr>
					<td style="width:180px;"><h5>기간</h5></td>
					<td style="width:150px;"><h5>제목</h5></td>
					<td style="width:80px;"><h5>여행지</h5></td>
					<td style="width:50px;"><h5>인원</h5></td>
					<td style="width:50px;"><h5>연령</h5></td>
				</tr>	
			</thead>	
			<tbody>
			
				<tr>
					<td style="text-align:left;" id="td_1">
						${getlist.date1} ~ ${getlist.date2}
					</td>
					<td>	
						<a onclick="gowithinfo(${ getlist.no })" class="btn btn-default" data-target="#myModal" data-toggle="modal" tabindex="-1" id="link">${ getlist.title }</a>
					</td>
					<td id="td_0">
						
						${ getlist.destination }
					</td>
					<td id="td_0">
						<c:if test="${getlist.mem== 2 }">
							2명
						</c:if>
						<c:if test="${getlist.mem== 34 }">
							3~4명
						</c:if>
						<c:if test="${getlist.mem== 58 }">
							5~8명
						</c:if>
						<c:if test="${getlist.mem== 8 }">
							8명이상
						</c:if>
						<c:if test="${getlist.mem== 100 }">
							선택안함
						</c:if>
					</td>
					<td id="td_0">
						<c:choose>
							<c:when test="${ getlist.age == 0 }">
								선택안함
							</c:when>
						 	<c:otherwise>
						 	${ getlist.age}대
						 	</c:otherwise>
						</c:choose>
					</td>
				</tr>
					</table>
					</c:if>
				</div>
		
		<c:if test="${requestScope.userID == sessionScope.userID}" >
		<a href="boardWrite.myboard?pagename=mypage" class="btn btn-default pull-right" type="submit">글쓰기</a>
</c:if>
				
	</div>
		
	</div>
	
	<br>
	<hr>
	<br>
	
<div id="wrapper">
	<h3  style="color:#FF5E00;">&#10094; 내 게시글 &#10095;</h3>
	<br>
	<section id="main">
		<section class="thumbnails">
		<div>
		<c:if test="${requestScope.boardList.size() != 0}" >
			<c:forEach var="board" items="${requestScope.boardList}"  varStatus="idx" >				
				<a onclick="readBoard(${ board.myboardID })" class="btn btn-default" data-target="#board" data-toggle="modal" tabindex="-1" style="background-color :rgba(0, 0, 0, 0.05);">

			<c:choose>
				<c:when test="${board.myboardFile == ''}" >
					<img src="images/공백.jpg" />
				</c:when>
				<c:otherwise>
					<img src="upload/${board.myboardFile }" />
				</c:otherwise>
			</c:choose>	

					<h3> ${board.myboardTitle } </h3>
					</a>
			 <c:if test="${(idx.count) != 9}" >
 	 			<c:if test="${(idx.count)%3 == 0}" >
					</div>
					<div>
				</c:if> 
			</c:if> 
				

			</c:forEach>
		</c:if>
					</div>
		</section>

				
			<c:choose>
				<c:when test="${requestScope.pageNumber == 1}" >
					<a class="prev2 disabled"
					style="font-size: 3rem; position: absolute; top: 40%; left: 3rem;">&#10094;&#10094;</a>
				</c:when>
				<c:otherwise>
					<a class="prev2" href="myPage.myboard?userID=${requestScope.userID}&pageNumber=${requestScope.pageNumber-1}"
					style="font-size: 3rem; position: absolute; top: 40%; left: 3rem;">&#10094;&#10094;</a>
				</c:otherwise>
			</c:choose>	
	

			<c:choose>
			<c:when test="${requestScope.beyond}" >
				<a class="next2" href="myPage.myboard?userID=${requestScope.userID}&pageNumber=${requestScope.pageNumber+1}" style="font-size: 3rem; position: absolute; top: 40%; right: 3rem;">&#10095;&#10095;</a>	
			</c:when>
				<c:otherwise>
					<a class="next2" style="font-size: 3rem; position: absolute; top: 40%; right: 3rem;">&#10095;&#10095;</a>
				</c:otherwise>
			</c:choose>	

	</section>
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
				 				<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
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
			
			
			
	<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" >
			<div class="modal-content">
				<div class="modal-header">
	
					<h4 class="modal-title" id="myModalLabel">동행 구하기</h4>
				</div>
				<div class="modal-body" id="getinfo">
				
				
				</div>
				<div class="modal-footer">
					<button type="button" onclick="" id="btn" style="display:none;" class="btn btn-default">삭제</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>	
	
	<div class="modal" id="board" role="dialog" aria-labelledby="myModalLabel" >
		<div class="modal-dialog modal-lg" role="document"  style="width:1200px; height:auto; display: table;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">게시글 보기</h4>
				</div>
				<div class="modal-body" id="boardinfo" >
				</div>
				<div class="modal-footer">
					<a href=""  id="updatebtn" class="btn btn-default">수정</a>
					<a href="" onclick="return confirm('정말로 삭제 하시겠습니까?');" id="delbtn" class="btn btn-default">삭제</a>

					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>			
			
			<hr>
			
			<jsp:include page="footer.jsp"/>

</body>
</html>