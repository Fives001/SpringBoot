package com.springboot.fives.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.fives.paging.PageNavigation;
import com.springboot.fives.service.WebcrawlerService;
import com.springboot.fives.vo.TermsNaverVO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebcrawlerController {
	
	@Autowired
	public final WebcrawlerService webcrawlerService=null;	

	public final PageNavigation pageNavigation=null;	
    
	
    @RequestMapping(value="/i")
    public ModelAndView insert(ModelAndView mv) throws IOException{
        ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
        result = webcrawlerService.getList();
        webcrawlerService.insertData(result);

        mv.addObject("result",result);

        //s.jsp에 결과를 전달
        mv.setViewName("s");        
        return mv;
    }
    
	@RequestMapping(value="/s")
    public ModelAndView select (ModelAndView mv,HttpServletRequest request) throws IOException{

        String startCount = request.getParameter("startCount")== null ? "0" : request.getParameter("startCount");

        ArrayList<TermsNaverVO> result = webcrawlerService.selectData(Integer.parseInt(startCount),10);
        PageNavigation pageNavigation = new PageNavigation();
        String paging = null;

        paging = pageNavigation.getPageLinks(Integer.parseInt(startCount) , webcrawlerService.getTotalCount(""), 10, 10);


        mv.addObject("result",result);
        mv.addObject("paging",paging);
        mv.addObject("totalCount",webcrawlerService.getTotalCount(""));


        //s.jsp에 결과를 전달
        mv.setViewName("s");        
        return mv;
	}  
    

	@RequestMapping(value="/t")
    public void test(){

        System.out.println("===============");
    }

}