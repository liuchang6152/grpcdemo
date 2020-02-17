package com.liuchang.grpcclient.controller;

import com.liuchang.grpcclient.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 14:38 2020/2/17
 * @ Description：
 * @ Modified By：
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api/bc/student", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StudentController {
    @Autowired
    private StudentServiceImpl studentService;

    @GetMapping
    public String getAddressBookInfo(String name){
        return studentService.getRealNameByUsername(name);
    }
}
