<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/custom.css">

<title>Trip_Planner</title>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="./jqplot/jquery.jqplot.min.js"></script>
<link rel="stylesheet" type="text/css" href="./jqplot/jquery.jqplot.min.css" />
<script type="text/javascript" src="./jqplot/plugins/jqplot.categoryAxisRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.pyramidAxisRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.pyramidGridRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.pyramidRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.canvasTextRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.canvasAxisLabelRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.pieRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.enhancedPieLegendRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.barRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.categoryAxisRenderer.js"></script>
<script type="text/javascript" src="./jqplot/plugins/jqplot.pointLabels.js"></script>
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="https://rawgit.com/jasondavies/d3-cloud/master/build/d3.layout.cloud.js" type="text/JavaScript"></script>
<script src="./js/bootstrap.min.js"></script>
<script type="text/javascript">

// 연령별 성별 회원 분포도
$(document).ready(function(){ 
    var ticks = ["1-5", "6-10", "11-15", "16-20", "21-25", "26-30", "31-35", "36-40", "41-45", "46-50", "51-55", "56-60", "61-65", "66-70", "71-75", "76-80", "81-85", "86-90", "91-95", "96-100", "101-105", "106-110"]; 
    
    var class1 = ${requestScope.class1}; 
    var class2 = ${requestScope.class2}; 

    var Colors = ["#526D2C", "#FF0000"]; 
    var plotOptions = { 
     title: '<div style="float: left; width: 50% ; text-align:center; color: black;">남자회원</div><div style="float: right; width: 50%; text-align:center; color: black;">여자회원</div><br><br>', 
     seriesColors: Colors, 
     grid: { 
      drawBorder: false, 
      shadow: false, 
      background: "white" 

     }, 
     defaultAxisStart: 0, 
     seriesDefaults: { 
      renderer: $.jqplot.PyramidRenderer, 
      rendererOptions: { 
       barPadding: 4 
      }, 
      yaxis: "yMidAxis", 
      xaxis: "xaxis", 
      shadow: true 
     }, 
     series: [ 
      { 
       rendererOptions:{ 
        side: 'left', 
        synchronizeHighlight: 2 
       } 
      } 
     ], 
     axes: { 

      xaxis: { 
       max: 1     
      }, 
      yMidAxis: { 
       label: '', 
       tickOptions: { 
       }, 
       ticks: ticks, 
       rendererOptions: { 
        category: true, 
        baselineWidth: 2 
       }, 
       numberTicks: 22, 
       showTicks: true 
      } 
     } 
    }; 

    plot1 = $.jqplot("chart1", [class1, class2], plotOptions); 
}); 

// 전체 게시물중 종류별 차지하는 양을 나타내는 파이 그래프
$(document).ready(function(){
	 
    data1 = ${requestScope.category};
    toolTip1 = ['Red Delicious Apples', 'Parson Brown Oranges', 'Cavendish Bananas'];
    var plot1 = jQuery.jqplot('chart2', 
        data1,
        {
            title: '', 
            seriesDefaults: {
                shadow: false, 
                renderer: jQuery.jqplot.PieRenderer, 
                rendererOptions: { padding: 2, sliceMargin: 2, showDataLabels: true }
            },
            legend: {
                show: true,
                location: 'e',
                renderer: $.jqplot.EnhancedPieLegendRenderer,
                rendererOptions: {
                    numberColumns: 1,
                    toolTips: toolTip1
                }
            },
        }
    );
});


// 상위 5개 도시 막대 그래프 
$(document).ready(function(){
	
	var line1 = ${requestScope.fivecity}
	
    $('#chart3').jqplot([line1], {
        title:'',
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {

                varyBarColor: true
            }
        },
        axes:{
            xaxis:{
                renderer: $.jqplot.CategoryAxisRenderer
            }
        }
    });
});

</script>
</head>
<style>
.jqplot-yMidAxis-label{
    width: 240px !important;
    left: -60px !important;
}
.row{
margin: 2rem;
}
.jqplot-axis{
font-size: 2rem;
}
.legend {
	border: 1px solid #555555;
	border-radius: 5px 5px 5px 5px;
	font-size: 0.8em;
	margin: 10px;
	padding: 8px;
}

.bld {
	font-weight: bold;
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



	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">

			<h1>사이트 분석</h1>	


		</div>
		<div class="col-md-3"></div>
	</div>


<hr>

	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">

			<h4>사이트 회원 연령별 남녀 비율</h4>	

		</div>
		<div class="col-md-3"></div>
	</div>


	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">
			<div id="chart1"></div>
		</div>
		<div class="col-md-3"></div>
	</div>



<hr>

	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">

			<h4>전체 게시물 분류별 비율</h4>	

		</div>
		<div class="col-md-3"></div>
	</div>


	<div class="row" style="margin-top: 5rem;">
		<div class="col-md-4"></div>
		<div class="col-md-4" style="text-align: center;">
			<div id="chart2"></div>
		</div>
		<div class="col-md-4"></div>
	</div>				


<hr>

	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">

			<h4>검색순위 상위 5개 도시</h4>	

		</div>
		<div class="col-md-3"></div>
	</div>

	
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4" style="text-align: center;">
				<div id="chart3" Style="margin-top: 4rem;"></div>		
			</div>
		<div class="col-md-4"></div>
	</div>	

<hr>


	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">

			<h4>회원 관심 키워드</h4>	

		</div>
		<div class="col-md-3"></div>
	</div>
	
	


	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="text-align: center;">
			<div id="wordcloud" align="center"></div>
			</div>
		<div class="col-md-3"></div>
	</div>	


	



	<jsp:include page="footer.jsp"/>
<script>
	var frequency_list = JSON.parse('${requestScope.list}');

	var x = frequency_list;
	var color = d3.scale.linear().domain(
			[ 0, 1, 2, 3, 4, 5, 6, 10, 15, 20, 100 ]).range(
			[ "#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666",
					"#555", "#444", "#333", "#222" ]);

	d3.layout.cloud().size([ 750, 300 ]).words(x).rotate(0).fontSize(
			function(d) {
				return d.size;
			}).on("end", draw).start();

	function draw(words) {
		d3.select("#wordcloud").append("svg").attr("width", 750).attr("height",
				350).attr("class", "wordcloud").append("g").attr("transform",
				"translate(320,200)").selectAll("text").data(words).enter()
				.append("text").style("font-size", function(d) {
					return d.size + "px";
				}).style("fill", function(d, i) {
					return color(i);
				}).attr(
						"transform",
						function(d) {
							return "translate(" + [ d.x*1.5, d.y*1.5 ] + ")rotate("
									+ d.rotate + ")";
						}).text(function(d) {
					return d.text;
				});
	}
</script>
</body>
</html>