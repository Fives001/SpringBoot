package com.springboot.fives.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springboot.fives.vo.TermsNaverVO;

@Service
public class WebcrawlerService {

  //  resources/application.properties에 저장된 DB 접속관련 정보를 가져온다.
  @Value("${spring.datasource.driver-class-name}")
  private String datasourceDriver;
  @Value("${spring.datasource.url}")
  String datasourceUrl;
  @Value("${spring.datasource.username}")
  String datasourceUserName;  
  @Value("${spring.datasource.password}")
  String datasourcePassword;

  @Value("${thumbnail.image.download.local.path}")
  String thumbnailPath;

  

  public int getTotalCount(String SQL) { 
    int count = 0;

    String JDBCClassName=datasourceDriver;
  
    // TODO Auto-generated method stub
    try {
      Class.forName(JDBCClassName);
    } catch (ClassNotFoundException e) {
      System.out.println("Not Found Class.....");
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  
 
    String     connurl  = datasourceUrl;
    String     user     = datasourceUserName;
    String     password = datasourcePassword;

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

  public String getPreDBContent(String id) {

    String returnId="";
      String JDBCClassName=datasourceDriver;
  
      // TODO Auto-generated method stub
      try {
        Class.forName(JDBCClassName);
      } catch (ClassNotFoundException e) {
        System.out.println("Not Found Class.....");
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
   
      String     connurl  = datasourceUrl;
      String     user     = datasourceUserName;
      String     password = datasourcePassword;
  
      try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
           
            String SQL = " select max(id) as id";
                   SQL+= " from terms_naver";
                   SQL+= " where id < ?";
            PreparedStatement pstmt = null;     

            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, id);
            ResultSet rs = null;
              rs = pstmt.executeQuery();
            if(rs.next()){     
              returnId=rs.getString("id");

       }            

          pstmt.close();
          connection.close();
      }
      catch (SQLException e) {
          e.printStackTrace();
      }
      return returnId;
  
  }

  public String getNextDBContent(String id) {
    String returnId="";
  
      String JDBCClassName=datasourceDriver;
  
      // TODO Auto-generated method stub
      try {
        Class.forName(JDBCClassName);
      } catch (ClassNotFoundException e) {
        System.out.println("Not Found Class.....");
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
   
      String     connurl  = datasourceUrl;
      String     user     = datasourceUserName;
      String     password = datasourcePassword;
  
      try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
           
            String SQL = " select MIN(id) as id";
                   SQL+= " from terms_naver";
                   SQL+= " where id > ?";
            PreparedStatement pstmt = null;     

            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, id);
            ResultSet rs = null;
              rs = pstmt.executeQuery();
            if(rs.next()){     
              returnId=rs.getString("id");
       }            

          pstmt.close();
          connection.close();
      }
      catch (SQLException e) {
          e.printStackTrace();
      }
      return returnId;
  
  }  



  public TermsNaverVO getDBContent(String id) {
		TermsNaverVO vo = new TermsNaverVO();
  
      String JDBCClassName=datasourceDriver;
  
      // TODO Auto-generated method stub
      try {
        Class.forName(JDBCClassName);
      } catch (ClassNotFoundException e) {
        System.out.println("Not Found Class.....");
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
   
      String     connurl  = datasourceUrl;
      String     user     = datasourceUserName;
      String     password = datasourcePassword;
  
      try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
           
            String SQL = " select id,title,content_url,content,thumb_url";
                   SQL+= " from terms_naver";
                   SQL+= " where id=?";
            PreparedStatement pstmt = null;     

            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, id);
            ResultSet rs = null;
              rs = pstmt.executeQuery();
            if(rs.next()){     
              vo.setId(rs.getString("id"));
              vo.setTitle(rs.getString("title"));
              vo.setContentUrl(rs.getString("content_url"));
              vo.setContent(rs.getString("content"));              
              vo.setThumbUrl(rs.getString("thumb_url"));
       }            

          pstmt.close();
          connection.close();
      }
      catch (SQLException e) {
          e.printStackTrace();
      }
      return vo;
  
  }

  public ArrayList<TermsNaverVO> selectData(int startCount , int pageViewCount) {
		ArrayList<TermsNaverVO> result = new ArrayList<TermsNaverVO>();
  
      String JDBCClassName=datasourceDriver;
  
      // TODO Auto-generated method stub
      try {
        Class.forName(JDBCClassName);
      } catch (ClassNotFoundException e) {
        System.out.println("Not Found Class.....");
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
   
      String     connurl  = datasourceUrl;
      String     user     = datasourceUserName;
      String     password = datasourcePassword;
  
      try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
           
            String SQL = "select id,title,content_url,content,thumb_url from terms_naver limit "+Integer.toString(startCount)+","+Integer.toString(pageViewCount);
            PreparedStatement pstmt = null;     

            pstmt = connection.prepareStatement(SQL);
            ResultSet rs = null;
  
            rs = pstmt.executeQuery();
            while(rs.next()){     
              TermsNaverVO vo = new TermsNaverVO();
              vo.setId(rs.getString("id"));
              vo.setTitle(rs.getString("title"));
              vo.setContentUrl(rs.getString("content_url"));
              vo.setContent(rs.getString("content"));              
              vo.setThumbUrl(rs.getString("thumb_url"));                            
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


//웹 수집한 데이터를 mariaDB에 입력
 public void insertData(ArrayList<Hashtable<String,String>> ar) {
		

  for (Hashtable<String,String> ht : ar) {

    String id = ht.get("id");
    String title = ht.get("title");
    String contentURL = ht.get("contentURL");
    String content = ht.get("content");
    String thumbUrl = ht.get("thumbUrl");    
    
    System.out.println("id:"+id);
    System.out.println("title:"+title);
    System.out.println("contentURL:https://terms.naver.com"+contentURL);
    System.out.println("content Length:"+content.length());
    System.out.println("thumbUrl:"+thumbUrl);    

    System.out.println("==============================================================");
  }


    String JDBCClassName=datasourceDriver;

		// TODO Auto-generated method stub
    try {
      Class.forName(JDBCClassName);
    } catch (ClassNotFoundException e) {
      System.out.println("Not Found Class.....");
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  
	  
    
    String     connurl  = datasourceUrl;
    String     user     = datasourceUserName;
    String     password = datasourcePassword;

    try (Connection connection = DriverManager.getConnection(connurl, user, password);) {
          connection.setAutoCommit(false);
          PreparedStatement pstmt = null;
          String SQL = "insert into terms_naver(id, title, content_url,content,thumb_url) values(?, ?, ?, ?,?)";

          pstmt = connection.prepareStatement(SQL);

          for (Hashtable<String,String> ht : ar) {
            String id = ht.get("id");
            String title = ht.get("title");
            String contentURL = ht.get("contentURL");
            String content = ht.get("content");
            String thumbUrl = ht.get("thumbUrl");                      
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
            pstmt.setString(5, thumbUrl);                 

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
 
 /*
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
   */
  
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



  public ArrayList<Hashtable<String,String>> getListTermsNaver() throws IOException{
    ArrayList<Hashtable<String,String>> ar = new ArrayList<Hashtable<String,String>>();
    String     baseURL="https://terms.naver.com";

       // specify the URL to retrieve
       String url = "https://terms.naver.com/list.naver?cid=60195&categoryId=60195&page=1";
       // specify the HTML element to search for
       String elementSelector = "div.thumb_area > div.thumb";
       for (int j = 1 ; j < 10 ; j++) {
         url = "https://terms.naver.com/list.naver?cid=60195&categoryId=60195&page="+j;
         // retrieve the web page as a JSoup document
         Document doc = Jsoup.connect(url).get();
         // select the desired HTML element(s)
         Elements elements = doc.select(elementSelector);
         Hashtable<String,String> ht = new Hashtable<String,String>();
         
         // iterate over the selected elements and extract the links


         for (Element element : elements) {
             Element link = element.selectFirst("a");
             Element imgInfo = element.selectFirst("a > img");             

                 ht = new Hashtable<String,String>();
                 String title = imgInfo.attr("alt");
                 String contentURL = baseURL+link.attr("href");
                 String content = getContent(contentURL);
                 String thumbUrl= imgInfo.attr("data-src"); 


                 System.out.println("title:"+title);		            		
                 System.out.println("contentURL:"+contentURL);
                 //System.out.println(content);
                 System.out.println(thumbUrl);

                 ht.put("id", generateURLId(contentURL));
                 ht.put("title", title);
                 ht.put("contentURL", contentURL);
                 ht.put("content", content);
                 ht.put("thumbUrl", thumbUrl);
                 
                 //섬네일 이미지를 로컬에 id명으로 저장한다.
                 System.out.println(saveImage(ht.get("thumbUrl"),ht.get("id")+".jpg"));
                  
                 ar.add(ht);

         }
       }
       return ar;
  }


  public boolean saveImage(String strUrl,String thumbnailFileName) throws IOException {

    URL url = null;
    InputStream in = null;
    OutputStream out = null;
    boolean isSaved=false;
    try {

        url = new URL(strUrl);
        in = url.openStream();
        out = new FileOutputStream(thumbnailPath+thumbnailFileName); //저장경로
        while(true){
            //이미지를 읽어온다.
            int data = in.read();
            if(data == -1){
                break;
            }
            //이미지를 쓴다.
            out.write(data);
        }
        in.close();
        out.close();
        isSaved=true;
    } catch (Exception e) {
        e.printStackTrace();
        isSaved=false;
    }finally{
        if(in != null){in.close();}
        if(out != null){out.close();}
    }
    return isSaved;
}  

}
