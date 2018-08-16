<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ajax测试页</title>
<script type="text/javascript">
//每1s用ajax得到page.ajaxCount直到ajaxCount与cName的collection的数据条数相等
var ajaxFlag=${page.ajaxFlag};
var i = window.setInterval("refreshPage()",1000);
function refreshPage(){
	if(ajaxFlag==1){
		window.clearInterval(i);
	}
	//var page = JSON.stringify(${page});
	//var page = JSONObject.fromObject(${page});
	var page = $.parseJSON(${page});
	$.ajax({
		url : "${pageContext.request.contextPath }/Test/utilAjax",
		type : "post",
		data : page,
		dataType : "json",
		contentType : "application/json;characterEncoding='utf-8'",
		success : function(){
			alert("success");
		},
		error : function(){
			alert("error");
		}
	});
}
</script>
</head>
<body>
<table border="1">
<tr><td>序号</td><td>id</td></tr>
<c:forEach items="${list }" var="dddd" varStatus="status">
	<tr><td>${status.index+1 }</td><td>${dddd._id }</td></tr>
</c:forEach>
</table>
<hr>
<c:if test="${page.pageNow!=1 }">
<a href="${pageContext.request.contextPath}/test/utilAjaxHome">首页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxPre">上一页</a>&nbsp;
</c:if>
<%-- <fmt:formatNumber type="number" value="${(page.ajaxCount-page.ajaxCount%page.pageSize)/page.pageSize }" maxFractionDigits="0" pattern="#" /> --%>
<%-- 
1. <fmt:formatNumber type="number" value="${5/6}" maxFractionDigits="0"/>
四舍五入结果为1
2. <fmt:formatNumber type="number" value="${(5-5%6)/6}" maxFractionDigits="0" pattern="#"/>
不四舍五入结果为0
另外这里加上pattern="#"表示一直以数字表示，默认情况下当超过3位数时会出现","分隔数字，如1,111 --%>
<%-- <span>当前第${page.pageNow }页/共${page.ajaxCount/page.pageSize }页,${page.ajaxCount }条</span> --%>
<%-- <span>当前第${page.pageNow }页/共<fmt:formatNumber type="number" value="${(page.ajaxCount-page.ajaxCount%page.pageSize)/page.pageSize }" maxFractionDigits="0" pattern="#" />页,${page.ajaxCount }条</span> --%>
<span>当前第${page.pageNow }页/共<fmt:formatNumber type="number" value="${(page.ajaxCount-1)/page.pageSize }" maxFractionDigits="0" pattern="#" />页,${page.ajaxCount }条</span>
<%-- <span>当前第${page.pageNow }页/共${(page.ajaxCount-1)/page.pageSize+1 }页,${page.ajaxCount }条</span>8.4 --%>
<%-- 
<c:if test="${page.pageNow!=page.pageCount }">
<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
</c:if>
 --%>
<!-- 判断下一页出现的条件及尾页出现的条件 -->
<c:if test="${page.pageNow!=page.pageCount }">
	<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
	<c:if test="${page.ajaxCount==page.cCount}">
		<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
	</c:if>
</c:if>
</body>
</html>
<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>testAjax测试页</title>
<script type="text/javascript" src="/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
//每1s用ajax得到page.ajaxCount直到ajaxCount与cName的collection的数据条数相等
var ajaxFlag=${map.page.ajaxFlag};
var i = window.setInterval("refreshPage()",3000);
function refreshPage(){
	if(ajaxFlag==1){
		window.clearInterval(i);
	}
	debugger
	var page = {
		"cName":"${map.page.cName}",
		"cCount":"${map.page.cCount}",
		"pageSize":"${map.page.pageSize}",
		"sortField":"${map.page.sortField}",
		"sortOrder":"${map.page.sortOrder}",
		"pageNow":"${map.page.pageNow}",
		"pageCount":"${map.page.pageCount}",
		"firstData":"${map.page.firstData}",
		"lastData":"${map.page.lastData}",
		"ajaxCount":"${map.page.ajaxCount}",
		"ajaxValue":"${map.page.ajaxValue}",
		"ajaxJump":"${map.page.ajaxJump}",
		"ajaxFlag":"${map.page.ajaxFlag}",
		"cFlag":"${map.page.cFlag}"
	};
	$.ajax({
		url : "${pageContext.request.contextPath }/test/utilAjax",
		type : "post",
		data : JSON.stringify(page),
		dataType : "json",
		contentType : "application/json;characterEncoding='utf-8'",
		success : function(data){
			alert(data.page.ajaxCount);
		},
		error : function(){
			alert("error");
		}
	});
}
function test(){
	alert(${map.page.ajaxCount});
}
</script>
</head>
<body>
<table border="1">
<tr><td>序号</td><td>id</td></tr>
<c:forEach items="${map.list }" var="dddd" varStatus="status">
	<tr><td>${status.index+1 }</td><td>${dddd._id }</td></tr>
</c:forEach>
</table>
<hr>
<c:if test="${map.page.pageNow!=1 }">
<a href="${pageContext.request.contextPath}/test/utilAjaxHome">首页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxPre">上一页</a>&nbsp;
</c:if>
<fmt:formatNumber type="number" value="${(map.page.ajaxCount-map.page.ajaxCount%map.page.pageSize)/map.page.pageSize }" maxFractionDigits="0" pattern="#" />

1. <fmt:formatNumber type="number" value="${5/6}" maxFractionDigits="0"/>
四舍五入结果为1
2. <fmt:formatNumber type="number" value="${(5-5%6)/6}" maxFractionDigits="0" pattern="#"/>
不四舍五入结果为0
另外这里加上pattern="#"表示一直以数字表示，默认情况下当超过3位数时会出现","分隔数字，如1,111
<span>当前第${map.page.pageNow }页/共${map.page.ajaxCount/map.page.pageSize }页,${map.page.ajaxCount }条</span>
<span>当前第${map.page.pageNow }页/共<fmt:formatNumber type="number" value="${(map.page.ajaxCount-map.page.ajaxCount%map.page.pageSize)/map.page.pageSize }" maxFractionDigits="0" pattern="#" />页,${map.page.ajaxCount }条</span>
<span>当前第${map.page.pageNow }页/共<fmt:formatNumber type="number" value="${(map.page.ajaxCount-1)/map.page.pageSize }" maxFractionDigits="0" pattern="#" />页,${map.page.ajaxCount }条</span>
<span>当前第${map.page.pageNow }页/共${(map.page.ajaxCount-1)/map.page.pageSize+1 }页,${map.page.ajaxCount }条</span>8.4

<c:if test="${map.page.pageNow!=map.page.pageCount }">
<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
</c:if>

<!-- 判断下一页出现的条件及尾页出现的条件 -->
<c:if test="${map.page.pageNow!=map.page.pageCount }">
	<a href="${pageContext.request.contextPath}/test/utilAjaxNext">下一页</a>&nbsp;
	<c:if test="${map.page.ajaxCount==map.page.cCount}">
		<a href="${pageContext.request.contextPath}/test/utilAjaxEnd">尾页</a>&nbsp;
	</c:if>
</c:if>
<input type="button" value="test" onclick="test()">
</body>
</html> --%>