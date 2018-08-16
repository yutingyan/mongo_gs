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
<%-- <c:if test="${map.page.pageNow!=1 }">
<a href="${pageContext.request.contextPath}/test/utilAjaxHome">首页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxPre">上一页</a>&nbsp;
</c:if>
<span>当前第${map.page.pageNow }页/共<fmt:formatNumber type="number" value="${(map.page.ajaxCount-(map.page.ajaxCount%map.page.pageSize==0?map.page.pageSize:map.page.ajaxCount%map.page.pageSize))/map.page.pageSize+1 }" maxFractionDigits="0" pattern="#" />页,${map.page.ajaxCount }条</span>
<!-- 判断下一页出现的条件及尾页出现的条件 -->
<c:if test="${map.page.pageNow!=map.page.pageCount && map.page.pageNow<((map.page.ajaxCount-(map.page.ajaxCount%map.page.pageSize==0?map.page.pageSize:map.page.ajaxCount%map.page.pageSize))/map.page.pageSize+1) }">
	<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
	<c:if test="${map.page.ajaxFlag==1}">
		<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
	</c:if>
</c:if> --%>
<c:if test="${map.ajaxPage.pageNow!=1 }">
<a href="${pageContext.request.contextPath}/test/utilAjaxHome">首页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxPre">上一页</a>&nbsp;
</c:if>
<span>当前第${map.ajaxPage.pageNow }页/共<fmt:formatNumber type="number" value="${(map.ajaxPage.ajaxCount-(map.ajaxPage.ajaxCount%map.ajaxPage.pageSize==0?map.ajaxPage.pageSize:map.ajaxPage.ajaxCount%map.ajaxPage.pageSize))/map.ajaxPage.pageSize+1 }" maxFractionDigits="0" pattern="#" />页,${map.ajaxPage.ajaxCount }条</span>
<!-- 判断下一页出现的条件及尾页出现的条件 -->
<c:if test="${map.ajaxPage.pageNow!=map.ajaxPage.pageCount && map.ajaxPage.pageNow<((map.ajaxPage.ajaxCount-(map.ajaxPage.ajaxCount%map.ajaxPage.pageSize==0?map.ajaxPage.pageSize:map.ajaxPage.ajaxCount%map.ajaxPage.pageSize))/map.ajaxPage.pageSize+1) }">
	<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
	<c:if test="${map.ajaxPage.ajaxFlag==1}">
		<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
	</c:if>
</c:if>
</span>
<%-- <input type="text" id="cName" value="${map.page.cName }" style="display: none">
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
<input type="text" id="cFlag" value="${map.page.cFlag }" style="display: none"> --%>
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
<script type="text/javascript">
var i = window.setInterval("refreshPage()",50);
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
			/* $("#cName").val(page1.cName);
			$("#cCount").val(page1.cCount);
			$("#pageSize").val(page1.pageSize);
			$("#sortField").val(page1.sortField);
			$("#sortOrder").val(page1.sortOrder);
			$("#pageNow").val(page1.pageNow);
			$("#pageCount").val(page1.pageCount);
			$("#firstData").val(page1.firstData);
			$("#lastData").val(page1.lastData);
			$("#ajaxCount").val(page1.ajaxCount);
			$("#ajaxValue").val(page1.ajaxValue);
			$("#ajaxJump").val(page1.ajaxJump);
			$("#ajaxFlag").val(page1.ajaxFlag);
			$("#cFlag").val(page1.cFlag); */
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
			
			//now
			/* $("#cName").val(${page.cName});
			$("#cCount").val(${page.cCount});
			$("#pageSize").val(${page.pageSize});
			$("#sortField").val(${page.sortField});
			$("#sortOrder").val(${page.sortOrder});
			$("#pageNow").val(${page.pageNow});
			$("#pageCount").val(${page.pageCount});
			$("#firstData").val(${page.firstData});
			$("#lastData").val(${page.lastData});
			$("#ajaxCount").val(${page.ajaxCount});
			$("#ajaxValue").val(${page.ajaxValue});
			$("#ajaxJump").val(${page.ajaxJump});
			$("#ajaxFlag").val(${page.ajaxFlag});
			$("#cFlag").val(${page.cFlag}); */
			
			/* var cName = ${page.cName}+"";
			var cCount = page.cCount;
			var pageSize = page.pageSize;
			var sortField = page.sortField;
			var sortOrder = page.sortOrder;
			var pageNow = page.pageNow;
			var pageCount = page.pageCount;
			var firstData = page.firstData;
			var lastData = page.lastData;
			var ajaxCount = page.ajaxCount;
			var ajaxValue = page.ajaxValue;
			var ajaxJump = page.ajaxJump;
			var ajaxFlag = page.ajaxFlag;
			var cFlag = page.cFlag;
			$("#cName").val(cName);
			$("#cCount").val(cCount);
			$("#pageSize").val(pageSize);
			$("#sortField").val(sortField);
			$("#sortOrder").val(sortOrder);
			$("#pageNow").val(pageNow);
			$("#pageCount").val(pageCount);
			$("#firstData").val(firstData);
			$("#lastData").val(lastData);
			$("#ajaxCount").val(ajaxCount);
			$("#ajaxValue").val(ajaxValue);
			$("#ajaxJump").val(ajaxJump);
			$("#ajaxFlag").val(ajaxFlag);
			$("#cFlag").val(cFlag); */
			
			var spanHtml = "";
			/* var jsonPage = JSON.stringify(page);
			if(page.pageNow!=1){
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
			
			/* if(page.pageNow!=1){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxHome'>首页</a>&nbsp;<a href='${pageContext.request.contextPath}/test/utilAjaxPre'>上一页</a>&nbsp;";
			}
			var count = Math.ceil((page.ajaxCount-1)/page.pageSize);
			spanHtml += "<span>当前第"+page.pageNow+"页/共"+count+"页,"+page.ajaxCount+"条</span>&nbsp;";
			if(page.pageNow!=page.pageCount && page.pageNow<count ){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxNext'>下一页</a>&nbsp;";
				if(page.ajaxFlag==1){
					spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxEnd'>尾页</a>&nbsp;";
				}
			} */
			
			/* var pageInfo = "?cName="+page.cName+"&cCount="+page.cCount+"&pageSize="+page.pageSize+"&sortField="+page.sortField+"&sortOrder="+page.sortOrder+"&pageNow="+page.pageNow+
					"&pageCount="+page.pageCount+"&firstData="+page.firstData+"&lastData="+page.lastData+"&ajaxCount="+page.ajaxCount+"&ajaxValue="+page.ajaxValue+"&ajaxJump="+page.ajaxJump+
					"&ajaxFlag="+page.ajaxFlag+"&cFlag="+page.cFlag; */
			//now
			var pageInfo = "?cName="+$("#cName").val()+"&cCount="+$("#cCount").val()+"&pageSize="+$("#pageSize").val()+"&sortField="+$("#sortField").val()+"&sortOrder="+$("#sortOrder").val()+"&pageNow="+$("#pageNow").val()+
					"&pageCount="+$("#pageCount").val()+"&firstData="+$("#firstData").val()+"&lastData="+$("#lastData").val()+"&ajaxCount="+$("#ajaxCount").val()+"&ajaxValue="+$("#ajaxValue").val()+"&ajaxJump="+$("#ajaxJump").val()+
					"&ajaxFlag="+$("#ajaxFlag").val()+"&cFlag="+$("#cFlag").val();
			/* var pageInfo = "?cName="+${page.cName}+"&cCount="+${page.cCount}+"&pageSize="+${page.pageSize}+"&sortField="+${page.sortField}+"&sortOrder="+${page.sortOrder}+"&pageNow="+${page.pageNow}+
					"&pageCount="+${page.pageCount}+"&firstData="+${page.firstData}+"&lastData="+${page.lastData}+"&ajaxCount="+${page.ajaxCount}+"&ajaxValue="+${page.ajaxValue}+"&ajaxJump="+${page.ajaxJump}+
					"&ajaxFlag="+${page.ajaxFlag}+"&cFlag="+${page.cFlag}; */
			/* var pageInfo = "?cName="+${page.cName}+""+"&cCount="+${page.cCount}+""+"&pageSize="+${page.pageSize}+""+"&sortField="+${page.sortField}+""+"&sortOrder="+${page.sortOrder}+""+"&pageNow="+${page.pageNow}+""+
					"&pageCount="+${page.pageCount}+""+"&firstData="+${page.firstData}+""+"&lastData="+${page.lastData}+""+"&ajaxCount="+${page.ajaxCount}+""+"&ajaxValue="+${page.ajaxValue}+""+"&ajaxJump="+${page.ajaxJump}+""+
					"&ajaxFlag="+${page.ajaxFlag}+""+"&cFlag="+${page.cFlag}+""; */
			/* var pageInfo = "?cName="+cName+"&cCount="+cCount+"&pageSize="+pageSize+"&sortField="+sortField+"&sortOrder="+sortOrder+"&pageNow="+pageNow+
					"&pageCount="+pageCount+"&firstData="+firstData+"&lastData="+lastData+"&ajaxCount="+ajaxCount+"&ajaxValue="+ajaxValue+"&ajaxJump="+ajaxJump+
					"&ajaxFlag="+ajaxFlag+"&cFlag="+cFlag; */
			//now
			if(page1.pageNow!=1){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxHome"+pageInfo+"'>首页</a>&nbsp;<a href='${pageContext.request.contextPath}/test/utilAjaxPre"+pageInfo+"'>上一页</a>&nbsp;";
			}
			var count = Math.ceil((page1.ajaxCount-1)/page1.pageSize);
			spanHtml += "<span>当前第"+page1.pageNow+"页/共"+count+"页,"+page1.ajaxCount+"条</span>&nbsp;";
			if(page1.pageNow!=page1.pageCount && page1.pageNow<count ){
				spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxNext"+pageInfo+"'>下一页</a>&nbsp;";
				if(page1.ajaxFlag==1){
					spanHtml += "<a href='${pageContext.request.contextPath}/test/utilAjaxEnd"+pageInfo+"'>尾页</a>&nbsp;";
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
</body>
</html>