package com.springboot.fives.list.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.fives.list.service.WebcrawlerService;
import com.springboot.fives.list.vo.PageInfoVO;
import com.springboot.fives.list.vo.TermsNaverVO;
import com.springboot.fives.paging.PageNavigation;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebcrawlerController {
	
	@Autowired
	public final WebcrawlerService webcrawlerService=null;	

	public final PageNavigation pageNavigation=null;	
    

    @RequestMapping(value="/i")
    public ModelAndView insert(ModelAndView mv) throws IOException{
        ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
        result = webcrawlerService.getListTermsNaver();
        webcrawlerService.insertData(result);

        mv.addObject("result",result);

        //s.jsp에 결과를 전달
        mv.setViewName("/list/s");        
        return mv;
    }
    
	@RequestMapping(value="/list/s")
    public ModelAndView select (ModelAndView mv,HttpServletRequest request) throws IOException{

        PageInfoVO pageInfoVO = PageInfoVO.builder()
                                .startCount(Integer.parseInt(request.getParameter("startCount")== null ? "0" : request.getParameter("startCount")))
                                .query(request.getParameter("query")== null ? "" : request.getParameter("query"))
                                .build();

        ArrayList<TermsNaverVO> result = webcrawlerService.selectData(pageInfoVO);
        PageNavigation pageNavigation = new PageNavigation();
        String paging = null;

        pageInfoVO.setTotalCount(webcrawlerService.getTotalCount(pageInfoVO,""));
        paging = pageNavigation.getPageLinks(pageInfoVO);


        mv.addObject("result",result);
        mv.addObject("paging",paging);
        mv.addObject("pageInfo",pageInfoVO);


        //s.jsp에 결과를 전달
        mv.setViewName("/list/s2");        
        return mv;
	}  
    

	@RequestMapping(value="/t")
    public void test() throws IOException{

        webcrawlerService.saveImage("https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F5382_000_1%2F20180430122455249_4WURTT458.png%2F%2525EB%25258F%252584%2525.png%3Ftype%3Dw540_fst_n%26wm%3DY%22&twidth=226&theight=226&opts=17","test002.jpg");
        System.out.println("===============");
    }

	@RequestMapping(value="/list/d")
    public ModelAndView detail(ModelAndView mv,HttpServletRequest request) throws IOException{

        String id = request.getParameter("id")== null ? "" : request.getParameter("id");
        PageInfoVO pageInfoVO = PageInfoVO.builder()
                                .startCount(Integer.parseInt(request.getParameter("startCount")== null ? "0" : request.getParameter("startCount")))
                                .query(request.getParameter("query")== null ? "" : request.getParameter("query"))
                                .build();
        
        mv.addObject("result",webcrawlerService.getDBContent(id));
        mv.addObject("preId",webcrawlerService.getPreDBContent(pageInfoVO,id));
        mv.addObject("nextId",webcrawlerService.getNextDBContent(pageInfoVO,id));                
        mv.addObject("pageInfo",pageInfoVO);                                


        mv.setViewName("/list/detail");        
        return mv;
    }    

}