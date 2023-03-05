package com.springboot.fives.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
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
        result = webcrawlerService.getListTermsNaver();
        webcrawlerService.insertData(result);

        mv.addObject("result",result);

        //s.jsp에 결과를 전달
        mv.setViewName("s");        
        return mv;
    }
    
	@RequestMapping(value="/s")
    public ModelAndView select (ModelAndView mv,HttpServletRequest request) throws IOException{

        int pageViewCount=5;
        String startCount = request.getParameter("startCount")== null ? "0" : request.getParameter("startCount");

        ArrayList<TermsNaverVO> result = webcrawlerService.selectData(Integer.parseInt(startCount),pageViewCount);
        PageNavigation pageNavigation = new PageNavigation();
        String paging = null;

        paging = pageNavigation.getPageLinks(Integer.parseInt(startCount) , webcrawlerService.getTotalCount(""), pageViewCount, 5);


        mv.addObject("result",result);
        mv.addObject("paging",paging);
        mv.addObject("totalCount",webcrawlerService.getTotalCount(""));
        mv.addObject("startCount",startCount);


        //s.jsp에 결과를 전달
        mv.setViewName("s");        
        return mv;
	}  
    

	@RequestMapping(value="/t")
    public void test() throws IOException{

        webcrawlerService.saveImage("https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fdbscthumb-phinf.pstatic.net%2F5382_000_1%2F20180430122455249_4WURTT458.png%2F%2525EB%25258F%252584%2525.png%3Ftype%3Dw540_fst_n%26wm%3DY%22&twidth=226&theight=226&opts=17","test002.jpg");
        System.out.println("===============");
    }

	@RequestMapping(value="/d")
    public ModelAndView detail(ModelAndView mv,HttpServletRequest request) throws IOException{

        String id = request.getParameter("id")== null ? "" : request.getParameter("id");
        String startCount = request.getParameter("startCount")== null ? "0" : request.getParameter("startCount");        
        
        mv.addObject("result",webcrawlerService.getDBContent(id));
        mv.addObject("preId",webcrawlerService.getPreDBContent(id));
        mv.addObject("nextId",webcrawlerService.getNextDBContent(id));                
        mv.addObject("startCount",startCount);                        


        mv.setViewName("detail");        
        return mv;
    }    

}