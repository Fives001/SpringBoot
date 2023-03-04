package com.springboot.fives.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.springboot.fives.vo.TermsNaverVO;

@Service
public class WebcrawlerService {


  public int getTotalCount(String SQL) { 
    int count = 0;

    String JDBCClassName="org.mariadb.jdbc.Driver";
  
    // TODO Auto-generated method stub
    try {
      Class.forName(JDBCClassName);
    } catch (ClassNotFoundException e) {
      System.out.println("Not Found Class.....");
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  
 
    String     connurl  = "jdbc:mariadb://localhost:3306/study";
    String     user     = "root";
    String     password = "1q2w3e4r";

    try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
         
          if ("".equals(SQL)){
            SQL = "select count(id) as cnt from terms_naver";
          }
          PreparedStatement pstmt = null;     

          pstmt = connection.prepareStatement(SQL);
          ResultSet rs = null;

          rs = pstmt.executeQuery();

          if(rs.next()){     
            count = rs.getInt("cnt");
     }            

        pstmt.close();
        connection.close();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }    


    
    return count;
  }

  public ArrayList<TermsNaverVO> selectData(int startCount , int pageViewCount) {
		ArrayList<TermsNaverVO> result = new ArrayList<TermsNaverVO>();
  
      String JDBCClassName="org.mariadb.jdbc.Driver";
  
      // TODO Auto-generated method stub
      try {
        Class.forName(JDBCClassName);
      } catch (ClassNotFoundException e) {
        System.out.println("Not Found Class.....");
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
   
      String     connurl  = "jdbc:mariadb://localhost:3306/study";
      String     user     = "root";
      String     password = "1q2w3e4r";
  
      try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
           
            String SQL = "select id,title,content_url from terms_naver limit "+Integer.toString(startCount)+","+Integer.toString(pageViewCount);
            PreparedStatement pstmt = null;     

            pstmt = connection.prepareStatement(SQL);
            ResultSet rs = null;
  
            rs = pstmt.executeQuery();
            while(rs.next()){     
              TermsNaverVO vo = new TermsNaverVO();
              vo.setId(rs.getString("id"));
              vo.setTitle(rs.getString("title"));
              vo.setContentUrl(rs.getString("content_url"));
              result.add(vo);
       }            

          pstmt.close();
          connection.close();
      }
      catch (SQLException e) {
          e.printStackTrace();
      }
      return result;
  
  }



 public void insertData(ArrayList<Hashtable<String,String>> ar) {
		
  for (Hashtable<String,String> ht : ar) {
    String id = ht.get("id");
    String title = ht.get("title");
    String contentURL = ht.get("contentURL");
    String content = ht.get("content");
    
    System.out.println("id:"+id);
    System.out.println("title:"+title);
    System.out.println("contentURL:https://terms.naver.com"+contentURL);
    System.out.println("content Length:"+content.length());
    System.out.println("==============================================================");
  }

    String JDBCClassName="org.mariadb.jdbc.Driver";

		// TODO Auto-generated method stub
    try {
      Class.forName(JDBCClassName);
    } catch (ClassNotFoundException e) {
      System.out.println("Not Found Class.....");
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  
	  
    
    System.out.println(".................Access DB Insert");
    String     connurl  = "jdbc:mariadb://localhost:3306/study";
    String     user     = "root";
    String     password = "1q2w3e4r";

    try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
          connection.setAutoCommit(false);
          PreparedStatement pstmt = null;
          String SQL = "insert into terms_naver(id, title, content_url,content) values(?, ?, ?, ?)";

          pstmt = connection.prepareStatement(SQL);

          for (Hashtable<String,String> ht : ar) {
            String id = ht.get("id");
            String title = ht.get("title");
            String contentURL = ht.get("contentURL");
            String content = ht.get("content");
            
            /*
            System.out.println("id:"+id);
            System.out.println("title:"+title);
            System.out.println("contentURL:https://terms.naver.com"+contentURL);
            System.out.println("content Length:"+content.length());
            System.out.println("==============================================================");
            */
            pstmt.setString(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, contentURL);
            pstmt.setString(4, content);            
            pstmt.addBatch();
            pstmt.clearParameters(); //파라미터 초기화
          }
          pstmt.executeBatch();
          pstmt.clearParameters(); //Batch 초기화          

        connection.commit();          
        pstmt.close();
        connection.close();
    }
    catch (SQLException e) {
        e.printStackTrace();
    }

}

 public static String getContent(String url) throws IOException{

        // specify the URL to retrieve
        //url = "https://terms.naver.com/entry.naver?docId=3537080&cid=60217&categoryId=60217";
      String result="";
        // specify the HTML element to search for
        String elementSelector = "div#size_ct";		 
        Document doc = Jsoup.connect(url).get();
        // select the desired HTML element(s)
        Elements elements = doc.select(elementSelector);		 
        for (Element element : elements) {
          //System.out.println(element.text());
          result = element.text();
        }
        return result;
 }
 
 public ArrayList<Hashtable<String,String>> getList() throws IOException{
     ArrayList<Hashtable<String,String>> ar = new ArrayList<Hashtable<String,String>>();
     String     baseURL="https://terms.naver.com";

        // specify the URL to retrieve
        String url = "https://terms.naver.com/list.naver?cid=60195&categoryId=60195&page=1";
        // specify the HTML element to search for
        String elementSelector = "div.info_area > div.subject > strong.title";
        for (int j = 1 ; j < 5 ; j++) {
          url = "https://terms.naver.com/list.naver?cid=60195&categoryId=60195&page="+j;
          // retrieve the web page as a JSoup document
          Document doc = Jsoup.connect(url).get();
          // select the desired HTML element(s)
          Elements elements = doc.select(elementSelector);
          Hashtable<String,String> ht = new Hashtable<String,String>();
          
          // iterate over the selected elements and extract the links


          for (Element element : elements) {
              Element link = element.selectFirst("a");
                  ht = new Hashtable<String,String>();
                  String title = link.text();
                  String contentURL = baseURL+link.attr("href");
                  String content = getContent(contentURL);
                //System.out.println((++row)+".	"+title);		            		
                  //System.out.println("------"+contentURL);
                  //System.out.println(content);
                  
                  ht.put("id", generateURLId(contentURL));
                  ht.put("title", title);
                  ht.put("contentURL", contentURL);
                  ht.put("content", content);
                  ar.add(ht);

          }
        }
        return ar;
   }
  
  //네이버 본문 URL에서 파라미터를 기본키로 문자열을 생성
  public String generateURLId(String url){

  StringBuffer sb = new StringBuffer();
    if (url !=null){
    String[] genUrl1 = url.split("\\?");
    String[] genUrl2 = genUrl1[1].split("&");
    //System.out.println("url:"+url);
    int row = 0;
    for (String str : genUrl2){
        row++;
        //System.out.println("str:"+str);
        if (row == 1){
        sb.append(str);
        }else{
            sb.append("_"+str);
        }
    }
    //System.out.println("sb:"+sb.toString());  
  } 
   return sb.toString();
  }



}
