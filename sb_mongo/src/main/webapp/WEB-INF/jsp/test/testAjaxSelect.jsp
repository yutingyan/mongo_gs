<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>testAjaxSelect测试页</title>
<script type="text/javascript" src="/jquery/jquery-2.1.1.min.js"></script>
</head>
<body>
<table border="1" id="search">
<tr><td>gt requSize:<input type="text" id="gtRequSize" value=""></td><td>lt requSize:<input type="text" id="ltRequSize" value=""></td></tr>
<tr><td colspan="2" align="right"><input type="button" value="查询" onclick="search()"></td></tr>
</table>
<hr/>
<table border="1" id="table">
<tr><td>序号</td><td>_id</td><td>requSize</td></tr>
<c:forEach items="${map.list }" var="dddd" varStatus="status">
	<tr><td>${status.index+1 }</td><td>${dddd._id }</td><td>${dddd.requSize }</td></tr>
</c:forEach>
</table>
<hr>
<span id="prefixHome" onclick="goToPage('Home')"></span>
<span id="prefixPre"  onclick="goToPage('Pre')"></span>
当前第<span id="one">1</span>页/共<span id="two">1</span>页,<span id="three">0</span>条
<span id="suffixNext" onclick="goToPage('Next')"></span>
<span id="suffixEnd" onclick="goToPage('End')"></span>
<%-- <span id="span">
<span id="prefix"></span>
<span id="change">当前第<span id="one">1</span>页/共<span id="two">1</span>页,<span id="three">0</span>条</span>
<!-- 判断下一页出现的条件及尾页出现的条件 -->
<c:if test="${map.ajaxPage.pageNow!=map.ajaxPage.pageCount && map.ajaxPage.pageNow<((map.ajaxPage.ajaxCount-(map.ajaxPage.ajaxCount%map.ajaxPage.pageSize==0?map.ajaxPage.pageSize:map.ajaxPage.ajaxCount%map.ajaxPage.pageSize))/map.ajaxPage.pageSize+1) }">
	<span id="suffixNext"><a onclick="goToPage('Next')">下一页</a>&nbsp;</span>
	<c:if test="${map.ajaxPage.ajaxFlag==1}">
		<span id="suffixEnd"><a onclick="goToPage('End')">尾页</a>&nbsp;</span>
	</c:if>
</c:if>
</span> --%>
<script type="text/javascript">
//在此设置全局变量
/* //page信息,注意字符串用双引号包起来
var cName = "${map.ajaxPage.cName }";
var cCount = ${map.ajaxPage.cCount };
var pageSize = ${map.ajaxPage.pageSize };
var sortField = "${map.ajaxPage.sortField }";
var sortOrder = ${map.ajaxPage.sortOrder };
var pageNow = ${map.ajaxPage.pageNow };
var pageCount = ${map.ajaxPage.pageCount };
var firstData = "${map.ajaxPage.firstData }";
var lastData = "${map.ajaxPage.lastData }";
var ajaxCount = ${map.ajaxPage.ajaxCount };
var ajaxValue = "${map.ajaxPage.ajaxValue }";
var ajaxJump = ${map.ajaxPage.ajaxJump };
var ajaxFlag = ${map.ajaxPage.ajaxFlag }; */
//四个不变的
var pageSize = ${map.pageSize };
var sortField = "${map.sortField }";
var sortOrder = ${map.sortOrder };
var ajaxJump = ${map.ajaxJump };
//两个暂时的
//var indexPageNow = ${map.indexPageNow};
//var idnexLastData = "${map.indexLastData}";
//六个do的
var cName = "${map.cName }";
var cCount = ${map.cCount };
var pageCount = ${map.pageCount };
var ajaxCount = ${map.ajaxCount };
var ajaxValue = "${map.ajaxValue }";
var ajaxFlag = ${map.ajaxFlag };
//三个go的
var pageNow = ${map.pageNow };
var firstData = "${map.firstData }";
var lastData = "${map.lastData }";

//查询条件
var srchGtrs;
var srchLtrs;

//test
//var i;

//时间
var time;
//刚开始就查询所有
search();
function search(){
	//更新时间
	var date = new Date();
	time = date.getTime();
	
	//每当查询的时候，将page属性还原为初始状态
	cCount = 0;
	pageCount = 0;
	ajaxCount = 0;
	ajaxValue = null;
	ajaxFlag = 0;
	pageNow = 1;
	//firstData = null;
	lastData = null;
	
	debugger
	
	//重新查询则重置	首页上一页当前第x页/共x页,x条下一页尾页
	$("#prefixHome").html("");
	$("#prefixPre").html("");
	$("#one").html(1);
	$("#two").html(1);
	$("#three").html(0);
	$("#suffixNext").html("");
	$("#suffixEnd").html("");
	
	srchGtrs = $("#gtRequSize").val();
	srchLtrs = $("#ltRequSize").val();
	
	refreshSearch();
}
function refreshSearch(){
	debugger
	/* var page = {
		"cName":cName,
		"cCount":cCount,
		//"pageSize":pageSize,
		//"sortField":sortField,
		//"sortOrder":sortOrder,
		"pageNow":pageNow,
		"pageCount":pageCount,
		//"firstData":firstData,
		"lastData":lastData,
		"ajaxCount":ajaxCount,
		"ajaxValue":ajaxValue,
		//"ajaxJump":ajaxJump,
		"ajaxFlag":ajaxFlag
	}; */
	
	var query = {
		"srchGtrs" : srchGtrs,
		"srchLtrs" : srchLtrs
	}
	
	var jsonData = {};
	//jsonData['page'] = page;
	
	debugger
	
	jsonData['cName'] = cName;
	jsonData['cCount'] = cCount;
	jsonData['pageCount'] = pageCount;
	jsonData['lastData'] = lastData;
	jsonData['ajaxCount'] = ajaxCount;
	jsonData['ajaxValue'] = ajaxValue;
	jsonData['ajaxFlag'] = ajaxFlag;
	
	jsonData['query'] = query;
	jsonData['time'] = time;
	
	doAjax(jsonData);
}
function doAjax(jsonData){
	$.ajax({
		url : "${pageContext.request.contextPath }/test/utilAjaxSearch",//不停的ajax
		data : JSON.stringify(jsonData),
		type : "post",
		dataType : "json",
		contentType : "application/json;characterEncoding='utf-8'",
		success : function(data){
			doSuccess(data);
		},
		error : function(){
			alert("error");
		}
	});
}
function doSuccess(data){
	//下一次点击会结束上一次点击的循环
	if(data.time<time){
		return;
	}
	//var page1 = data.ajaxPage;
	//cName = page1.cName;
	cCount = data.cCount;
	//pageSize = page1.pageSize;
	//sortField = page1.sortField;
	//sortOrder = page1.sortOrder;
	//pageNow = page1.pageNow;
	pageCount = data.pageCount;
	//firstData = page1.firstData;
	if(data.lastData!=null){
		lastData = data.lastData;
	}
	ajaxCount = data.ajaxCount;
	ajaxValue = data.ajaxValue;
	//ajaxJump = page1.ajaxJump;
	ajaxFlag = data.ajaxFlag;
	
	//alert("doSuccess\najaxCount----"+ajaxCount+"\npageNow----"+pageNow+"\nfirstData----"+firstData+"\nlastData----"+lastData);
	
	var count = 0;
	if(ajaxCount==0){
		count = 0;
		$("#table").empty();
		$("#table").append("<tr><td>序号</td><td>_id</td><td>requSize</td></tr>");
	}else{
		count = Math.ceil(ajaxCount/pageSize);
	}
	$("#two").html(count);
	$("#three").html(ajaxCount);
	
	debugger
	if(pageNow<count){
		$("#suffixNext").html("下一页");
		if(ajaxFlag==1){
			$("#suffixEnd").html("尾页");
		}else{
			$("#suffixEnd").html("");
		}
	}else{
		$("#suffixNext").html("");
	}
	
	var list = data.list;
	var len = list.length;
	if(len>0){
		var tableHtml = "<tr><td>序号</td><td>_id</td><td>requSize</td></tr>";
		for(var i=0;i<len;i++){
			tableHtml += "<tr><td>"+(i+1)+"</td><td>"+list[i]._id+"</td><td>"+list[i].requSize+"</td></tr>";
		}
		$("#table").empty();
		$("#table").append(tableHtml);
	}
	//循环
	if(ajaxFlag!=1){
		refreshSearch();
	}
}
function goToPage(flag){
	debugger
	/* var page = {
		"cName":cName,
		//"cCount":cCount,
		//"pageSize":pageSize,
		//"sortField":sortField,
		//"sortOrder":sortOrder,
		"pageNow":pageNow,
		//"pageCount":pageCount,
		"firstData":firstData,
		"lastData":lastData,
		//"ajaxCount":ajaxCount,
		//"ajaxValue":ajaxValue,
		//"ajaxJump":ajaxJump,
		//"ajaxFlag":ajaxFlag
	}; */
	var query = {
		"srchGtrs" : srchGtrs,
		"srchLtrs" : srchLtrs
	}
	
	var jsonData = {};
	//jsonData['page'] = page;
	
	jsonData['cName'] = cName;
	jsonData['pageNow'] = pageNow;
	jsonData['firstData'] = firstData;
	jsonData['lastData'] = lastData;
	jsonData['flag'] = flag;
	
	jsonData['pageCount'] = pageCount;
	jsonData['ajaxValue'] = ajaxValue;
	
	jsonData['cCount'] = cCount;
	
	jsonData['query'] = query;
	
	$.ajax({
		url : "${pageContext.request.contextPath }/test/utilAjaxSelect",
		type : "post",
		data : JSON.stringify(jsonData),
		dataType : "json",
		contentType : "application/json;characterEncoding='utf-8'",
		success : function(data){
			debugger
			//var page1 = data.ajaxPage;
			//cName = page1.cName;
			//cCount = page1.cCount;
			//pageSize = page1.pageSize;
			//sortField = page1.sortField;
			//sortOrder = page1.sortOrder;
			pageNow = data.pageNow;
			//pageCount = page1.pageCount;
			firstData = data.firstData;
			lastData = data.lastData;
			//ajaxCount = page1.ajaxCount;
			//ajaxValue = page1.ajaxValue;
			//ajaxJump = page1.ajaxJump;
			//ajaxFlag = page1.ajaxFlag;
			
			//alert("goToPage\najaxCount----"+ajaxCount+"\npageNow----"+pageNow+"\nfirstData----"+firstData+"\nlastData----"+lastData);
			
			if(pageNow!=1){
				$("#prefixHome").html("首页");
				$("#prefixPre").html("上一页");
			}else{
				$("#prefixHome").html("");
				$("#prefixPre").html("");
			}
			$("#one").html(pageNow);
			
			debugger
			var count = 0;
			if(ajaxCount==0){
				count = 0;
				$("#table").empty();
				$("#table").append("<tr><td>序号</td><td>_id</td><td>requSize</td></tr>");
			}else{
				count = Math.ceil(ajaxCount/pageSize);
			}
			if(pageNow<count){
				$("#suffixNext").html("下一页");
				if(ajaxFlag==1){
					$("#suffixEnd").html("尾页");
				}else{
					$("#suffixEnd").html("");
				}
			}else{
				$("#suffixNext").html("");
				$("#suffixEnd").html("");
			}
			
			var list = data.list;
			var len = list.length;
			if(len>0){
				var tableHtml = "<tr><td>序号</td><td>_id</td><td>requSize</td></tr>";
				for(var i=0;i<len;i++){
					tableHtml += "<tr><td>"+(i+1)+"</td><td>"+list[i]._id+"</td><td>"+list[i].requSize+"</td></tr>";
				}
				$("#table").empty();
				$("#table").append(tableHtml);
			}
		},
		error : function(){
			alert("error");
		}
	});
}
</script>
</body>
</html>