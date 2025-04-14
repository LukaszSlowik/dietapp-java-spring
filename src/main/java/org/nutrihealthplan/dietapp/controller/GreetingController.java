package org.nutrihealthplan.dietapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GreetingController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public String user(){
        return "Hello, user";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        return "Hello, admin";
    }
}
