package com.springboot.fives.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.fives.login.service.MemberService;
import com.springboot.fives.login.vo.User;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired 
    MemberService memberService;

    @RequestMapping(value="/member/login")
    public ModelAndView login(ModelAndView mv) throws IOException{
        //login.jsp에 결과를 전달
        mv.setViewName("/member/login");        
        return mv;
    }

    @RequestMapping(value="/member/checkMember")
    public String checkMember(HttpServletRequest request) throws IOException{
        
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String redirectString ="";

        User memberVO = new User();
        memberVO.setId(id);
        memberVO.setPassword(password);

        System.out.println(memberVO.getId());
        System.out.println(memberVO.getPassword());

        System.out.println("memberService.checkMember(memberVO):"+memberService.checkMember(memberVO));        

        if  (memberService.checkMember(memberVO) ){
            redirectString="redirect:/list/s";
        }else{
            redirectString="redirect:/member/login";
        }
        return redirectString;
    }    

    @RequestMapping(value="/member/memberRegist")
    public ModelAndView memberRegist(ModelAndView mv) throws IOException{
        //login.jsp에 결과를 전달
        mv.setViewName("/member/memberRegist");        
        return mv;
    }
    
}
