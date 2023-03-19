<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>멤버등록</title>
</head>
<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">


<script src="/js/jquery-3.2.1.slim.min.js" ></script>
<script src="/js/jquery-1.6.2.min.js" ></script>

<script src="/js/popper.min.js" ></script>
<script src="/js/bootstrap.min.js" ></script>

<script>
  function saveMember(){
    var frm = document.registForm;
    var id = frm.id.value;
    var password = frm.password.value;

    if (id == null || id==''){
      alert('이메일을 입력하세요');
      frm.id.focus();
      return false;
    }

    if (password == null || password ==''){
      alert('암호를 입력하세요');
      frm.password.focus();
      return false;
    }

    $.ajax({
                type : "POST",              // HTTP method type(GET, POST) 형식이다.
                url : "/member/saveMember",      // 컨트롤러에서 대기중인 URL 주소이다.
                data : {"id":id ,"password":password},            // Json 형식의 데이터이다.
                dataType:"json",
                success : function(json){ // 비동기통신의 성공일경우 success콜백으로 들어옵니다. 'res'는 응답받은 데이터이다.
                    // 응답코드 > 0000
                    if (json.isSuccess){
                      alert('등록되었습니다.');
                      location.href="/member/login";
                      return true;
                    }else{
                      alert('등록에 실패했습니다.');
                    return false;
                    } 
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){ // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
                    alert("통신 실패.")
                }
            });    
    return false;
  }
</script>

<section class="vh-100">
    <div class="container-fluid h-custom">
      <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-md-9 col-lg-6 col-xl-5">
          <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
            class="img-fluid" alt="Sample image">
        </div>
        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
          <form onsubmit="return saveMember()" name="registForm">
  
            <!-- Email input -->
            <div class="form-outline mb-4">
              <input type="email" id="form3Example3" class="form-control form-control-lg"
                placeholder="Enter a valid email address" name="id"/>
              <label class="form-label" for="form3Example3">Email address</label>
            </div>
  
            <!-- Password input -->
            <div class="form-outline mb-3">
              <input type="password" id="form3Example4" class="form-control form-control-lg"
                placeholder="Enter password" name="password"/>
              <label class="form-label" for="form3Example4">Password</label>
            </div>
  
            <div class="text-center text-lg-start mt-4 pt-2">
              <button type="button" class="btn btn-primary btn-lg"
                style="padding-left: 2.5rem; padding-right: 2.5rem;" onclick="javascript:saveMember()">등록</button>
            </div>
  
          </form>
        </div>
      </div>
    </div>
    <div
      class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-primary">
      <!-- Copyright -->
      <div class="text-white mb-3 mb-md-0">
        Copyright © 2020. All rights reserved.
      </div>
      <!-- Copyright -->
  
      <!-- Right -->
      <div>
        <a href="#!" class="text-white me-4">
          <i class="fab fa-facebook-f"></i>
        </a>
        <a href="#!" class="text-white me-4">
          <i class="fab fa-twitter"></i>
        </a>
        <a href="#!" class="text-white me-4">
          <i class="fab fa-google"></i>
        </a>
        <a href="#!" class="text-white">
          <i class="fab fa-linkedin-in"></i>
        </a>
      </div>
      <!-- Right -->
    </div>
  </section>