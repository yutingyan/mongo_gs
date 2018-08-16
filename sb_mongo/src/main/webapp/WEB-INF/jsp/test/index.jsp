<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页</title>
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
<a href="${pageContext.request.contextPath}/test/utilHome">首页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilPre">上一页</a>&nbsp;
</c:if>
<span>当前第${page.pageNow }页/共${page.pageCount }页,${page.cCount }条</span>
<c:if test="${page.pageNow!=page.pageCount }">
<a href="${pageContext.request.contextPath}/test/utilNext">下一页</a>&nbsp;
<a href="${pageContext.request.contextPath}/test/utilEnd">尾页</a>&nbsp;
</c:if>
</body>
</html>