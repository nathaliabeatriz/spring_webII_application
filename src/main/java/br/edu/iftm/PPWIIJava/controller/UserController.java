package br.edu.iftm.PPWIIJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class UserController {
    @GetMapping("/user")
    public String getMethodName() {
        return "user/index";
    }
}