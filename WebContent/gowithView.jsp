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
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link href="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
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
   
   function searchcheck(){
	   var date1=$("#depart").val();
	   var date2=$("#return").val();
	   
	   if(date1 !=""){
		   if(date2 ==""){
			   alert("도착날자를 선택해 주세요");
			   return false;
		   }
	   }else{return true;}
   };
   
   
   
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
 		
 	function gowithchat(toID){
 		var toID=toID;
 		$.ajax({
 			type : 'POST',
 			url : './GowithChat.ajax',
 			data : { toID : toID},
 			datatype : 'json',
 			success : function(data){
 				var chat = JSON.parse(data);
 				if(chat.no=="-1"){
 					$("#chatbtn").css("display", "none");
 					$("#chatmsg").css("display", "");
 					$("#chatmsg").val("자기자신에게는 메세지를 보낼수 없습니다.");
 					$("#chattable").css("display", "none");	
 				}else{
 				$("#chatbtn").css("display", "");
 				$("#chatmsg").css("display", "none");
 				$("#chattable").css("display", "");
 				$("#chatimg").attr("src",chat.toProfile);
 				$("#toID").val(chat.toID);
 				}
 			}
			})
		};
		
		
		function submitFunction(){
			var fromID = '${ sessionScope.userID }';
			var toID = $('#toID').val();
			var chatContent = $('#chatContent').val();
			$.ajax({
				type: 'POST',
				url	: './ChatSubmit.ajax',
				data: {
					fromID: encodeURIComponent(fromID),
					toID: encodeURIComponent(toID),
					chatContent: encodeURIComponent(chatContent)
				},
				success: function(result){
					if(result == 1 ){
						alert("메세지 전송에 성공하였습니다");
						$("#chatModal").hide();
					}else if (result == 0){
						alert("메세지를 입력해주세요");
					}
					$('#chatContent').val('');
				}
				
			});			
		}
		
		function gowithdelete(no){
			var no=no
			$.ajax({
	 			type : 'POST',
	 			url : './GowithDelete.ajax',
	 			data : { no : no},
	 			datatype : 'json',
	 			success : function(data){	
						alert("삭제하였습니다");
						location.reload();		
	 			}
			
		});
		}
		
		function gowithlike(no){
			var no=no
			$.ajax({
	 			type : 'POST',
	 			url : './GowithLike.ajax',
	 			data : { no : no},
	 			datatype : 'json',
	 			success : function(data){
	 				location.reload();			
	 			}
			
		});
		}
		

</script>
<style type="text/css">

#td,#td1{
padding-left: 20px;
}
form{
width: 70%;
margin:100px auto;
}
.modal-backdrop.in{
z-index: 0;
}

#td_0{
 padding-top: 30px;
}
#td_1{
 padding-top: 15px;
}
</style>

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
		<hr>
		<form action="gowithsearch.with" method="post" onsubmit="return searchcheck()">
		
		<table width="800" height="100" align="center">
			<tr>
				<td align="left">
					-기간 <input type="text" id="depart" name="date1" placeholder="depart date" value="${requestScope.date1}">~
					<input type="text" id="return" name="date2" placeholder="return date" value="${requestScope.date2}">
				</td>
				<td align="left">
					-인원 
<input type="radio" name="mem"  value="2" <c:if test="${ requestScope.mem == '2' }">  checked  </c:if> >2명		
<input type="radio" name="mem"  value="34" <c:if test="${ requestScope.mem == '34' }">  checked  </c:if>  >3~4명
<input type="radio" name="mem"  value="58" <c:if test="${ requestScope.mem == '58' }">  checked  </c:if>  >5~8명
<input type="radio" name="mem"  value="8" <c:if test="${ requestScope.mem == '8' }">  checked  </c:if>  >8명 이상
<input type="radio" name="mem"  value="0" <c:if test="${ requestScope.mem == '0' }">  checked  </c:if>  >선택안함
					
				</td>
			</tr>
			<tr>
				<td align="left">
					-여행지<input type="text" name="destination" value="${requestScope.destination}">
				</td>
				<td align="left">
					-연령대 
<input type="radio" name="age"  value="10" <c:if test="${ requestScope.age == '10' }">  checked  </c:if> >10대		
<input type="radio" name="age"  value="20" <c:if test="${ requestScope.age == '20' }">  checked  </c:if>  >20대
<input type="radio" name="age"  value="30" <c:if test="${ requestScope.age == '30' }">  checked  </c:if>  >30대
<input type="radio" name="age"  value="40" <c:if test="${ requestScope.age == '40' }">  checked  </c:if>  >40대
<input type="radio" name="age"  value="0" <c:if test="${ requestScope.age == '0' }">  checked  </c:if>  >선택안함

				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" class="btn btn-success" value="검색">
				</td>
			</tr>
		</table>
		</form>
		<hr>
		
		<div class="container">
  <h2>동행구하기</h2>
  <p>동행을구하고자하시는 분은 자유롭게 등록을 하시고, 찾으시는분은 마음에드는 동행을 골라보세요

  <a href="gowithgetlike.with" class="btn btn-default mx-1 mt-2 pull-right" >
  <img src="images/love.png" width="20" height="20"> &nbsp;찜 보기</a>
	<a href="gowithwrite.with" class="btn btn-success mx-1 mt-2 pull-right" type="submit">등록하기</a>
  </p>            
  <table class="table table-striped">
    <thead>
      <tr>
        <th colspan="6"></th>
      </tr>
    </thead>
    <tbody>    
    	<c:if test="${requestScope.getlist ==null}">
    	
    		<tr>
    			<td colspan="5">
    				등록된 프로필이 없습니다.
    			</td>
    		</tr>
		</c:if>    
		
		<c:if test="${requestScope.getlist != null }">
		
			<c:forEach var="getlist" items="${requestScope.getlist}" varStatus="status">
			
				<tr height="40">
					<td>
						<img src="${getlist.profilephoto }" width="70" height="70">
						${ getlist.userid }</td>
					<td style="text-align:left;" id="td_1">
						${getlist.date1} ~ ${getlist.date2} <br/>
						<a onclick="gowithinfo(${ getlist.no })" class="btn btn-default" data-target="#myModal" data-toggle="modal" tabindex="-1" id="link">${ getlist.title }</a></td>
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
					<td id="td_0">
						<a onclick="gowithchat('${ getlist.userid }')" data-target="#chatModal" data-toggle="modal" >
  						<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> &nbsp;&nbsp;&nbsp;
  						</a>	
  						<c:if test="${getlist.likecheck ==1 }">
  							<a onclick="gowithlike(${ getlist.no })" id="ulike">
  								<img src="images/love.png" width="20" height="20">
  							</a>
  						</c:if>
  						<c:if test="${getlist.likecheck == null }">
  							<a onclick="gowithlike(${ getlist.no })" id="ulike">
  								<img src="images/love2.png" width="20" height="20">
  							</a>
  						</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		
		
		
		<ul class="pagination" style="margin: 0 auto;">
			<c:choose>
				<c:when test="${requestScope.startPage != 1}" >
					<li>
						<a href="gowithsearch.with?pageNumber=${requestScope.startPage-1}">
							<span class="glyphicon glyphicon-chevron-left"></span>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<span class="glyphicon glyphicon-chevron-left" style="color:gray;"></span>
					</li>
				</c:otherwise>
			</c:choose>

			<c:forEach var="num" begin="${requestScope.startPage}" end="${requestScope.pageNumber-1}" step="1" varStatus="status">
				<li>
<a href="gowithsearch.with?pageNumber=${num}&destination=${requestScope.destination}&date1=${requestScope.date1}&date2=${requestScope.date2}&age=${requestScope.age}&mem=${requestScope.mem}">${num}</a>
				</li>
			</c:forEach>
		
			<li class="active"><a href="gowithsearch.with?pageNumber=${requestScope.pageNumber}&searchType=${requestScope.searchType}">${requestScope.pageNumber}</a></li>

			<c:forEach var="num" begin="${requestScope.pageNumber+1}" end="${requestScope.pageNumber+requestScope.targetPage}" step="1" varStatus="status">
				<c:if test="${num < (requestScope.startPage+10)}" >
					<li>
<a href="gowithsearch.with?pageNumber=${num}&destination=${requestScope.destination}&date1=${requestScope.date1}&date2=${requestScope.date2}&age=${requestScope.age}&mem=${requestScope.mem}">${num}</a>
					</li>
				</c:if>
			</c:forEach>
			
			<c:choose>
				<c:when test="${(requestScope.pageNumber+requestScope.targetPage) > (requestScope.startPage+9)}" >
					<li>
						<a href="gowithsearch.with?pageNumber=${requestScope.startPage+10}">
							<span class="glyphicon glyphicon-chevron-right"></span>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<span class="glyphicon glyphicon-chevron-right" style="color:gray;"></span>
					</li>
				</c:otherwise>
			</c:choose>
		
		</ul>
		

    </tbody>
  </table>
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
	
			
			<!-- -------------------------------------------------------------- -->
			
			
	<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
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
	
		<!-- -------------------------------------------------------------- -->
	<div class="modal" id="chatModal" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" >
			<div class="modal-content">
				<div class="modal-header">
					메세지보내기
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body">
					<input type="text" id="chatmsg" style="display:none; border:none" size="50">
					<table id="chattable">
						<tr>
							<td rowspan="2"><img id="chatimg" src="" width="170" height="170"></td>
							<td id="td1"> to.<input type="text" id="toID" style="border:none"> </td>
						</tr>
						<tr>
							<td id="td">
							<textarea style="height:150px; width: 370px;" id="chatContent" class="form-control" placeholder="메시지를 입력하시오" maxlength="100" onKeyDown="javascript:if (event.keyCode == 13) submitFunction();"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="chatbtn" class="btn btn-default pull-right" onclick="submitFunction()">전송</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>	
	<jsp:include page="footer.jsp"/>
</body>
</html>