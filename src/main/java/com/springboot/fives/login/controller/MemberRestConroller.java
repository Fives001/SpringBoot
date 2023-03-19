package com.springboot.fives.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.springboot.fives.login.service.MemberService;
import com.springboot.fives.login.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MemberRestConroller {
    @Autowired
    MemberService memberService;

    @ResponseBody
    @RequestMapping(value="/member/saveMember")
    public String memberSave(ModelAndView mv,HttpServletRequest request) throws IOException{

        User memberVO = new User();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String password = request.getParameter("password") == null ? "" : request.getParameter("password");        


        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                
        memberVO.setId(id);
        memberVO.setPassword(password);

        JsonObject obj =new JsonObject();
        obj.addProperty("isSuccess", memberService.saveMember(memberVO));

        //login.jsp에 결과를 전달

        return obj.toString();
    }     
}
