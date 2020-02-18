package com.liuchang.grpcclient.service;

import com.liuchang.grpcclient.Client.StudentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 13:29 2020/2/17
 * @ Description：
 * @ Modified By：
 */
@Service
public class StudentServiceImpl {
   @Autowired
   private StudentClient studentClient;

   public String getRealNameByUsername(String name){
      String username = studentClient.getRealNameByUsername(name);
      System.out.println("-------------username:"+username+"---------------------");
      return username;
   }

   public List<Map> getStudentsByAge(int age) throws Exception {
      List<Map> mapList = new ArrayList<>();
      mapList= studentClient.getStudentsByAge(age);
      System.out.println("-------------mapList:"+mapList+"---------------------");
      return mapList;
   }

   public List<Map> getStudentsWrapperByAges(int age) throws Exception{
      List<Map> mapList = new ArrayList<>();
      mapList= studentClient.getStudentsWrapperByAges(age);
      System.out.println("-------------mapList:"+mapList+"---------------------");
      return mapList;
   }
}
