
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 용어사전</title>
</head>
<link rel="stylesheet" href="css/bootstrap.min.css" >
<script src="js/jquery-3.2.1.slim.min.js" ></script>
<script src="js/popper.min.js" ></script>
<script src="js/bootstrap.min.js" ></script>


<script>
    function doPaging(startCount){
        location.href="/s?startCount="+startCount;
    }


</script>

<body>
<ul class="list-group">
<c:forEach var="item" items="${result}">
<li class="list-group-item d-flex justify-content-between align-items-center" style="font-size: small;">
        <!-- <img style="width:80px;height:30%"src="/images/${item.id}.jpg"/> -->
        <a href="${item.contentUrl}">${item.title}</a>
        <a href="/d?id=${item.id}&startCount=${startCount}">내용보기</a>
        <span class="badge badge-primary badge-pill">${fn:length(item.content)}</span>
    </li>

</c:forEach>
</ul>
<br>
<br>

<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        ${paging}
    </ul>
</nav>


</body>
</html>