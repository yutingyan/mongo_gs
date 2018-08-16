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
<c:if test="${map.page.pageNow!=1 }">
<a href="${pageContext.request.contextPath}/test/utilAjaxHome">首页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxPre">上一页</a>&nbsp;
</c:if>
<%-- <fmt:formatNumber type="number" value="${(map.page.ajaxCount-map.page.ajaxCount%map.page.pageSize)/map.page.pageSize }" maxFractionDigits="0" pattern="#" /> --%>
<%-- 
1. <fmt:formatNumber type="number" value="${5/6}" maxFractionDigits="0"/>
四舍五入结果为1
2. <fmt:formatNumber type="number" value="${(5-5%6)/6}" maxFractionDigits="0" pattern="#"/>
不四舍五入结果为0
另外这里加上pattern="#"表示一直以数字表示，默认情况下当超过3位数时会出现","分隔数字，如1,111 --%>
<%-- <span>当前第${map.page.pageNow }页/共${map.page.ajaxCount/map.page.pageSize }页,${map.page.ajaxCount }条</span> --%>
<%-- <span>当前第${map.page.pageNow }页/共<fmt:formatNumber type="number" value="${(map.page.ajaxCount-map.page.ajaxCount%map.page.pageSize)/map.page.pageSize }" maxFractionDigits="0" pattern="#" />页,${map.page.ajaxCount }条</span> --%>
<%-- <span>当前第${map.page.pageNow }页/共<fmt:formatNumber type="number" value="${(map.page.ajaxCount-1)/map.page.pageSize+1 }" maxFractionDigits="0" pattern="#" />页,${map.page.ajaxCount }条</span> --%>
<span>当前第${map.page.pageNow }页/共<fmt:formatNumber type="number" value="${(map.page.ajaxCount-(map.page.ajaxCount%map.page.pageSize==0?map.page.pageSize:map.page.ajaxCount%map.page.pageSize))/map.page.pageSize+1 }" maxFractionDigits="0" pattern="#" />页,${map.page.ajaxCount }条</span>
<%-- <span>当前第${map.page.pageNow }页/共<span id="showCount"></span>页,${map.page.ajaxCount }条</span> --%>
<%-- <span>当前第${map.page.pageNow }页/共${(map.page.ajaxCount-1)/map.page.pageSize+1 }页,${map.page.ajaxCount }条</span>8.4 --%>
<%-- 
<c:if test="${map.page.pageNow!=map.page.pageCount }">
<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
</c:if>
 --%>
<!-- 判断下一页出现的条件及尾页出现的条件 -->
<c:if test="${map.page.pageNow!=map.page.pageCount && map.page.pageNow<((map.page.ajaxCount-(map.page.ajaxCount%map.page.pageSize==0?map.page.pageSize:map.page.ajaxCount%map.page.pageSize))/map.page.pageSize+1) }">
	<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
	<c:if test="${map.page.ajaxFlag==1}">
		<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
	</c:if>
</c:if>
</span>
<input type="text" id="cName" value="${map.page.cName }" style="display: none">
<input type="text" id="cCount" value="${map.page.cCount }" style="display: none">
<input type="text" id="pageSize" value="${map.page.pageSize }" style="display: none">
<input type="text" id="sortField" value="${map.page.sortField }" style="display: none">
<input type="text" id="sortOrder" value="${map.page.sortOrder }" style="display: none">
<input type="text" id="pageNow" value="${map.page.pageNow }" style="display: none">
<input type="text" id="pageCount" value="${map.page.pageCount }" style="display: none">
<input type="text" id="firstData" value="${map.page.firstData }" style="display: none">
<input type="text" id="lastData" value="${map.page.lastData }" style="display: none">
<input type="text" id="ajaxCount" value="${map.page.ajaxCount }" style="display: none">
<input type="text" id="ajaxValue" value="${map.page.ajaxValue }" style="display: none">
<input type="text" id="ajaxJump" value="${map.page.ajaxJump }" style="display: none">
<input type="text" id="ajaxFlag" value="${map.page.ajaxFlag }" style="display: none">
<input type="text" id="cFlag" value="${map.page.cFlag }" style="display: none">
<script type="text/javascript">
var i = window.setInterval("refreshPage()",1000);
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
			var page = data.page;
			$("#cName").val(page.cName);
			$("#cCount").val(page.cCount);
			$("#pageSize").val(page.pageSize);
			$("#sortField").val(page.sortField);
			$("#sortOrder").val(page.sortOrder);
			$("#pageNow").val(page.pageNow);
			$("#pageCount").val(page.pageCount);
			$("#firstData").val(page.firstData);
			$("#lastData").val(page.lastData);
			$("#ajaxCount").val(page.ajaxCount);
			$("#ajaxValue").val(page.ajaxValue);
			$("#ajaxJump").val(page.ajaxJump);
			$("#ajaxFlag").val(page.ajaxFlag);
			$("#cFlag").val(page.cFlag);
			
			var jsonPage = JSON.stringify(page);
			
			var spanHtml = "";
			/* if(page.pageNow!=1){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxHome'>首页</a>&nbsp;<a href='${pageContext.request.contextPath}/test/utilAjaxPre'>上一页</a>&nbsp;";
			}
			var count = Math.ceil((page.ajaxCount-1)/page.pageSize);
			spanHtml += "<span>当前第"+page.pageNow+"页/共"+count+"页,"+page.ajaxCount+"条</span>&nbsp;";
			if(page.pageNow!=page.pageCount && page.pageNow<count ){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxNext?page="+window.encodeURIComponent(jsonPage)+"'>下一页</a>&nbsp;";
				if(page.ajaxFlag==1){
					spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxEnd'>尾页</a>&nbsp;";
				}
			} */
			if(page.pageNow!=1){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxHome'>首页</a>&nbsp;<a href='${pageContext.request.contextPath}/test/utilAjaxPre'>上一页</a>&nbsp;";
			}
			var count = Math.ceil((page.ajaxCount-1)/page.pageSize);
			spanHtml += "<span>当前第"+page.pageNow+"页/共"+count+"页,"+page.ajaxCount+"条</span>&nbsp;";
			if(page.pageNow!=page.pageCount && page.pageNow<count ){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxNext'>下一页</a>&nbsp;";
				if(page.ajaxFlag==1){
					spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxEnd'>尾页</a>&nbsp;";
				}
			}
			$("#span").empty();
			$("#span").append(spanHtml);
		},
		error : function(){
			alert("error");
		}
	});
}
</script>
<%-- var list = data.list;
			var len = list.length;
			var tableHtml = "";
			for(var i=0;i<len;i++){
				tableHtml += "<tr><td>"+(i+1)+"</td><td>"+list[i].id+"</td></tr>";
			}
			$("#table").empty();
			$("#table").append(tableHtml); --%>
</body>
</html>