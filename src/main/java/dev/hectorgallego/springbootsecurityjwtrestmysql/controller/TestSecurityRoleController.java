package dev.hectorgallego.springbootsecurityjwtrestmysql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestSecurityRoleController {

    @GetMapping("/securityadmin")
    public String security(){
        return "Solo para administradores";
    }

    @GetMapping("/securityuseradmin")
    public String security2(){
        return "Solo para usuarios y administradores";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
    
}
