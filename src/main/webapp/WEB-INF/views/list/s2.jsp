
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
<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">


<script src="/js/jquery-3.2.1.slim.min.js" ></script>
<script src="/js/popper.min.js" ></script>
<script src="/js/bootstrap.min.js" ></script>
<script>
    
    function doPaging(startCount){
        var frm = document.searchForm;
        frm.action.value="s";
        frm.startCount.value=startCount;
        frm.submit();
    }

    function goSearch(){
        var frm = document.searchForm;
        frm.action="s";
        frm.startCount.value="0";
        return true;
    }

    function goDetail(id){
        var frm = document.searchForm;
        frm.id.value=id;
        frm.action="d";
        frm.submit();
    }    

</script>

<body>
<div class="container">
    <form onsubmit="return goSearch()" id="searchForm" name="searchForm">
        <div class="form-group">
            <label for="inputAddress">Search</label>
            <input type="text" class="form-control" id="query" name="query" placeholder="제목검색.." value="${pageInfo.query}">
        </div>
    <button type="submit" class="btn btn-primary">검색</button>

    <input type="hidden" name="startCount" value="${pageInfo.startCount}">
    <input type="hidden" name="id" value="">    

</form>



<c:forEach var="item" items="${result}">
    <div class="media" >   
            <img class="align-self-center mr-3" style="border:1px solid black" src="/images/${item.id}.jpg" >
        <div class="media-body">
            <h5 class="mt-0"><a href="${item.contentUrl}">${item.title}</a></h5>
            <p><a href="javascript:goDetail('${item.id}')">내용보기</a></p>
            <p><span class="badge badge-primary badge-pill">${fn:length(item.content)}</span></p>
        </div>
    </div>
</c:forEach>

</div>

<br>
<br>

<c:if test="${pageInfo.totalCount != 0}">
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        ${paging}
    </ul>
</nav>
</c:if>


</body>
</html>