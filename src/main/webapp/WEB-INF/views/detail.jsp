
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 용어사전 상세보기</title>
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
    <h3>${result.title}</h3>
<br>
${result.content}

<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${empty preId}">
                <li class="page-item">
                    <a class="page-link" href="#">이전자료보기xxxxx</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item active">
                    <a class="page-link" href="/d?id=${preId}&startCount=${startCount}">이전자료보기</a>                    
                </li>
            </c:otherwise>
        </c:choose>


        <c:choose>
            <c:when test="${empty nextId}">        
                <li class="page-item">
                    <a class="page-link" href="#">다음자료보기</a>                    
                </li>
            </c:when>
            <c:otherwise>            
                <li class="page-item active">
                    <a class="page-link" href="/d?id=${nextId}&startCount=${startCount}">다음자료보기</a>
                </li>
            </c:otherwise>
        </c:choose>
        <li class="page-item active">
            <a class="page-link" href="/s?startCount=${startCount}">리스트로</a>                    
        </li>        
</ul>
</nav>
</body>
</html>