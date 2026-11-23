package com.example.video_2_Build_First_Spring_Boot_Application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "Hello world";
    }
}
