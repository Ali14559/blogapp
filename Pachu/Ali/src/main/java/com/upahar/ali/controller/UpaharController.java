package com.upahar.ali.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UpaharController {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/ali")
    public  String ali(){
        return "Ali for Admin only";
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")

    @GetMapping("/alii")
    public String alii(){
        return "Ali for Admin & User";
    }
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/aliii")
    public String aliii(){
        return "Ali for User only";
    }
}
