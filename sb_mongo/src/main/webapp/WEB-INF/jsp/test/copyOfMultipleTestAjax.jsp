<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>testAjax测试页</title>
<script type="text/javascript" src="/jquery/jquery-2.1.1.min.js"></script>
</head>
<body>
<table border="1" id="table">
<tr><td>序号</td><td>id</td></tr>
<c:forEach items="${map.list }" var="dddd" varStatus="status">
	<tr><td>${status.index+1 }</td><td>${dddd._id }</td></tr>
</c:forEach>
</table>
<hr>
<span id="span">
<c:if test="${map.ajaxPage.pageNow!=1 }">
<a onclick="goToPage('Home')">首页</a>&nbsp;
<a onclick="goToPage('Pre')">上一页</a>&nbsp;
</c:if>
<span>当前第${map.ajaxPage.pageNow }页/共<fmt:formatNumber type="number" value="${(map.ajaxPage.ajaxCount-(map.ajaxPage.ajaxCount%map.ajaxPage.pageSize==0?map.ajaxPage.pageSize:map.ajaxPage.ajaxCount%map.ajaxPage.pageSize))/map.ajaxPage.pageSize+1 }" maxFractionDigits="0" pattern="#" />页,${map.ajaxPage.ajaxCount }条</span>
<!-- 判断下一页出现的条件及尾页出现的条件 -->
<c:if test="${map.ajaxPage.pageNow!=map.ajaxPage.pageCount && map.ajaxPage.pageNow<((map.ajaxPage.ajaxCount-(map.ajaxPage.ajaxCount%map.ajaxPage.pageSize==0?map.ajaxPage.pageSize:map.ajaxPage.ajaxCount%map.ajaxPage.pageSize))/map.ajaxPage.pageSize+1) }">
	<a onclick="goToPage('Next')">下一页</a>&nbsp;
	<c:if test="${map.ajaxPage.ajaxFlag==1}">
		<a onclick="goToPage('End')">尾页</a>&nbsp;
	</c:if>
</c:if>
</span>
<input type="text" id="cName" value="${map.ajaxPage.cName }" style="display: none">
<input type="text" id="cCount" value="${map.ajaxPage.cCount }" style="display: none">
<input type="text" id="pageSize" value="${map.ajaxPage.pageSize }" style="display: none">
<input type="text" id="sortField" value="${map.ajaxPage.sortField }" style="display: none">
<input type="text" id="sortOrder" value="${map.ajaxPage.sortOrder }" style="display: none">
<input type="text" id="pageNow" value="${map.ajaxPage.pageNow }" style="display: none">
<input type="text" id="pageCount" value="${map.ajaxPage.pageCount }" style="display: none">
<input type="text" id="firstData" value="${map.ajaxPage.firstData }" style="display: none">
<input type="text" id="lastData" value="${map.ajaxPage.lastData }" style="display: none">
<input type="text" id="ajaxCount" value="${map.ajaxPage.ajaxCount }" style="display: none">
<input type="text" id="ajaxValue" value="${map.ajaxPage.ajaxValue }" style="display: none">
<input type="text" id="ajaxJump" value="${map.ajaxPage.ajaxJump }" style="display: none">
<input type="text" id="ajaxFlag" value="${map.ajaxPage.ajaxFlag }" style="display: none">
<input type="text" id="cFlag" value="${map.ajaxPage.cFlag }" style="display: none">
<input type="text" id="asd" value="" style="display: none">
<script type="text/javascript">
var i = window.setInterval("refreshPage()",5000);
function refreshPage(){
	//每1s用ajax得到page.ajaxCount直到ajaxCount与cName的collection的数据条数相等
	var ajaxFlag=$("#ajaxFlag").val();
	if(ajaxFlag==1){
		window.clearInterval(i);
	}
	var cName = $("#cName").val();
	var cCount = $("#cCount").val();
	var pageSize = $("#pageSize").val();
	var sortField = $("#sortField").val();
	var sortOrder = $("#sortOrder").val();
	var pageNow = $("#pageNow").val();
	var pageCount = $("#pageCount").val();
	var firstData = $("#firstData").val();
	var lastData = $("#lastData").val();
	var ajaxCount = $("#ajaxCount").val();
	var ajaxValue = $("#ajaxValue").val();
	var ajaxJump = $("#ajaxJump").val();
	var ajaxFlag = $("#ajaxFlag").val();
	var cFlag = $("#cFlag").val();
	var page = {
		"cName":cName,
		"cCount":cCount,
		"pageSize":pageSize,
		"sortField":sortField,
		"sortOrder":sortOrder,
		"pageNow":pageNow,
		"pageCount":pageCount,
		"firstData":firstData,
		"lastData":lastData,
		"ajaxCount":ajaxCount,
		"ajaxValue":ajaxValue,
		"ajaxJump":ajaxJump,
		"ajaxFlag":ajaxFlag,
		"cFlag":cFlag
	};
	$.ajax({
		url : "${pageContext.request.contextPath }/test/utilAjax",
		type : "post",
		data : JSON.stringify(page),
		dataType : "json",
		contentType : "application/json;characterEncoding='utf-8'",
		success : function(data){
			var page1 = data.ajaxPage;
			$("#cName").attr("value",page1.cName);
			$("#cCount").attr("value",page1.cCount);
			$("#pageSize").attr("value",page1.pageSize);
			$("#sortField").attr("value",page1.sortField);
			$("#sortOrder").attr("value",page1.sortOrder);
			$("#pageNow").attr("value",page1.pageNow);
			$("#pageCount").attr("value",page1.pageCount);
			$("#firstData").attr("value",page1.firstData);
			$("#lastData").attr("value",page1.lastData);
			$("#ajaxCount").attr("value",page1.ajaxCount);
			$("#ajaxValue").attr("value",page1.ajaxValue);
			$("#ajaxJump").attr("value",page1.ajaxJump);
			$("#ajaxFlag").attr("value",page1.ajaxFlag);
			$("#cFlag").attr("value",page1.cFlag);
			
			var spanHtml = "";
			/* var pageInfo = "?cName="+$("#cName").val()+"&cCount="+$("#cCount").val()+"&pageSize="+$("#pageSize").val()+"&sortField="+$("#sortField").val()+"&sortOrder="+$("#sortOrder").val()+"&pageNow="+$("#pageNow").val()+
					"&pageCount="+$("#pageCount").val()+"&firstData="+$("#firstData").val()+"&lastData="+$("#lastData").val()+"&ajaxCount="+$("#ajaxCount").val()+"&ajaxValue="+$("#ajaxValue").val()+"&ajaxJump="+$("#ajaxJump").val()+
					"&ajaxFlag="+$("#ajaxFlag").val()+"&cFlag="+$("#cFlag").val();
			if(page1.pageNow!=1){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxHome"+pageInfo+"'>首页</a>&nbsp;<a href='${pageContext.request.contextPath}/test/utilAjaxPre"+pageInfo+"'>上一页</a>&nbsp;";
			}
			var count = Math.ceil((page1.ajaxCount-1)/page1.pageSize);
			spanHtml += "<span>当前第"+page1.pageNow+"页/共"+count+"页,"+page1.ajaxCount+"条</span>&nbsp;";
			if(page1.pageNow!=page1.pageCount && page1.pageNow<count ){
				spanHtml += "<a onclick='goToPage(Next)'>下一页</a>&nbsp;";
				if(page1.ajaxFlag==1){
					spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxEnd"+pageInfo+"'>尾页</a>&nbsp;";
				}
			} */
			if(page1.pageNow!=1){
				spanHtml += "<a onclick='goToPage(\"Home\")'>首页</a>&nbsp;<a onclick='goToPage(\"Pre\")'>上一页</a>&nbsp;";
			}
			var count = Math.ceil((page1.ajaxCount-1)/page1.pageSize);
			spanHtml += "<span>当前第"+page1.pageNow+"页/共"+count+"页,"+page1.ajaxCount+"条</span>&nbsp;";
			if(page1.pageNow!=page1.pageCount && page1.pageNow<count ){
				spanHtml += "<a onclick='goToPage(\"Next\")'>下一页</a>&nbsp;";
				if(page1.ajaxFlag==1){
					spanHtml += "<a onclick='goToPage(\"End\")'>尾页</a>&nbsp;";
				}
			}
			/* if(page1.pageNow!=1){
				spanHtml += '<a onclick="goToPage('Home')">首页</a>&nbsp;<a onclick="goToPage('Pre')">上一页</a>&nbsp;';
			}
			var count = Math.ceil((page1.ajaxCount-1)/page1.pageSize);
			spanHtml += "<span>当前第"+page1.pageNow+"页/共"+count+"页,"+page1.ajaxCount+"条</span>&nbsp;";
			if(page1.pageNow!=page1.pageCount && page1.pageNow<count ){
				spanHtml += '<a onclick="goToPage('Next')">下一页</a>&nbsp;';
				if(page1.ajaxFlag==1){
					spanHtml += '<a onclick="goToPage('End')">尾页</a>&nbsp;';
				}
			} */
			$("#span").empty();
			$("#span").append(spanHtml);
		},
		error : function(){
			alert("error");
		}
	});
}
function goToPage(flag){
	var cName = $("#cName").val();
	var cCount = $("#cCount").val();
	var pageSize = $("#pageSize").val();
	var sortField = $("#sortField").val();
	var sortOrder = $("#sortOrder").val();
	var pageNow = $("#pageNow").val();
	var pageCount = $("#pageCount").val();
	var firstData = $("#firstData").val();
	var lastData = $("#lastData").val();
	var ajaxCount = $("#ajaxCount").val();
	var ajaxValue = $("#ajaxValue").val();
	var ajaxJump = $("#ajaxJump").val();
	var ajaxFlag = $("#ajaxFlag").val();
	var cFlag = $("#cFlag").val();
	var page = {
		"cName":cName,
		"cCount":cCount,
		"pageSize":pageSize,
		"sortField":sortField,
		"sortOrder":sortOrder,
		"pageNow":pageNow,
		"pageCount":pageCount,
		"firstData":firstData,
		"lastData":lastData,
		"ajaxCount":ajaxCount,
		"ajaxValue":ajaxValue,
		"ajaxJump":ajaxJump,
		"ajaxFlag":ajaxFlag,
		"cFlag":cFlag
	};
	$.ajax({
		url : "${pageContext.request.contextPath }/test/utilAjax"+flag,
		type : "post",
		data : JSON.stringify(page),
		dataType : "json",
		contentType : "application/json;characterEncoding='utf-8'",
		success : function(data){
			var page1 = data.ajaxPage;
			$("#cName").attr("value",page1.cName);
			$("#cCount").attr("value",page1.cCount);
			$("#pageSize").attr("value",page1.pageSize);
			$("#sortField").attr("value",page1.sortField);
			$("#sortOrder").attr("value",page1.sortOrder);
			$("#pageNow").attr("value",page1.pageNow);
			$("#pageCount").attr("value",page1.pageCount);
			$("#firstData").attr("value",page1.firstData);
			$("#lastData").attr("value",page1.lastData);
			$("#ajaxCount").attr("value",page1.ajaxCount);
			$("#ajaxValue").attr("value",page1.ajaxValue);
			$("#ajaxJump").attr("value",page1.ajaxJump);
			$("#ajaxFlag").attr("value",page1.ajaxFlag);
			$("#cFlag").attr("value",page1.cFlag);
			
			var spanHtml = "";
			if(page1.pageNow!=1){
				spanHtml += "<a onclick='goToPage(\"Home\")'>首页</a>&nbsp;<a onclick='goToPage(\"Pre\")'>上一页</a>&nbsp;";
			}
			var count = Math.ceil((page1.ajaxCount-1)/page1.pageSize);
			spanHtml += "<span>当前第"+page1.pageNow+"页/共"+count+"页,"+page1.ajaxCount+"条</span>&nbsp;";
			if(page1.pageNow!=page1.pageCount && page1.pageNow<count ){
				spanHtml += "<a onclick='goToPage(\"Next\")'>下一页</a>&nbsp;";
				if(page1.ajaxFlag==1){
					spanHtml += "<a onclick='goToPage(\"End\")'>尾页</a>&nbsp;";
				}
			}
			$("#span").empty();
			$("#span").append(spanHtml);
			
			var list = data.list;
			var len = list.length;
			var tableHtml = "<tr><td>序号</td><td>id</td></tr>";
			for(var i=0;i<len;i++){
				tableHtml += "<tr><td>"+(i+1)+"</td><td>"+list[i]._id+"</td></tr>";
			}
			$("#table").empty();
			$("#table").append(tableHtml);
			
			
		},
		error : function(){
			alert("error");
		}
	});
}
</script>
</body>
</html>