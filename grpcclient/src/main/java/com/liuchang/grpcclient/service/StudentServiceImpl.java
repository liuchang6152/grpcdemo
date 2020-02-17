package com.liuchang.grpcclient.service;

import com.liuchang.grpcclient.Client.StudentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
