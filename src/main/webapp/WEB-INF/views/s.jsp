
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<script>
    function doPaging(startCount){
        location.href="/s?startCount="+startCount;
    }


</script>

<body>
<c:forEach var="item" items="${result}">
<a href="${item.contentUrl}">${item.title}</a><br>
<hr/><br>
</c:forEach>

totalCount:${totalCount}<br>
${paging}<br>









</h1>


</body>
</html>