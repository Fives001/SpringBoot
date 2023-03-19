package com.springboot.fives.login.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springboot.fives.login.vo.User;

@Service
public class MemberService {

  //  resources/application.properties에 저장된 DB 접속관련 정보를 가져온다.
  @Value("${spring.datasource.driver-class-name}")
  private String datasourceDriver;
  @Value("${spring.datasource.url}")
  String datasourceUrl;
  @Value("${spring.datasource.username}")
  String datasourceUserName;  
  @Value("${spring.datasource.password}")
  String datasourcePassword;    


    public boolean saveMember(User memberVO){
        boolean isSuccess = false;

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

            Connection connection = null;
            try {
                  connection = DriverManager.getConnection(connurl, user, password);
                  connection.setAutoCommit(false);
                  PreparedStatement pstmt = null;
                  String SQL = "insert into member(id, password) values(?, ?)";
                  pstmt = connection.prepareStatement(SQL);
                  pstmt.setString(1, memberVO.getId());
                  pstmt.setString(2, memberVO.getPassword());
                  pstmt.executeUpdate();
       
                connection.commit();          
                pstmt.close();
                connection.close();
                isSuccess = true;
            }
            catch (SQLException e) {
                //e.printStackTrace();
                if (connection != null){
                  try {
                    connection.rollback();
                    connection.close();                       
                  } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                  }
               
                }

                isSuccess = false;                
            }        

        return isSuccess;
    }


    public boolean checkMember(User memberVO){
      boolean isSuccess = false;

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

          Connection connection = null;
          try {
                connection = DriverManager.getConnection(connurl, user, password);
                connection.setAutoCommit(false);
                PreparedStatement pstmt = null;
                String SQL = "select COUNT(id) as cnt from member where id=? and password=?";

                pstmt = connection.prepareStatement(SQL);
                pstmt.setString(1, memberVO.getId());
                pstmt.setString(2, memberVO.getPassword()); 

                int resultCnt=0;
                ResultSet rs = null;
                rs = pstmt.executeQuery();                
                if(rs.next()){     
                  resultCnt = rs.getInt("cnt");
                }
              connection.commit();          
              pstmt.close();
              connection.close();
              if (resultCnt == 1 ){
                isSuccess = true;

              }else if (resultCnt == 1){
                isSuccess = false;
              }

          }
          catch (SQLException e) {
              //e.printStackTrace();
              if (connection != null){
                try {
                  connection.rollback();
                  connection.close();                       
                } catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }
              }
              isSuccess = false;                
          }        

      return isSuccess;
  }    

  public User getUserInfo(User memberVO){
    boolean isSuccess = false;
    User userVO = new User();       
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

        Connection connection = null;
        try {
              connection = DriverManager.getConnection(connurl, user, password);
              connection.setAutoCommit(false);
              PreparedStatement pstmt = null;
              String SQL = "select COUNT(id) as cnt from member where id=? and password=?";

              pstmt = connection.prepareStatement(SQL);
              pstmt.setString(1, memberVO.getId());
              pstmt.setString(2, memberVO.getPassword()); 

              int resultCnt=0;
              ResultSet rs = null;
              rs = pstmt.executeQuery();                
              if(rs.next()){     
                resultCnt = rs.getInt("cnt");
              }
            connection.commit();          
            pstmt.close();
            connection.close();
            if (resultCnt == 1 ){
              isSuccess = true;

            }else if (resultCnt == 1){
              isSuccess = false;
            }

        }
        catch (SQLException e) {
            //e.printStackTrace();
            if (connection != null){
              try {
                connection.rollback();
                connection.close();                       
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
            isSuccess = false;                
        }


    return userVO;
}      
}
