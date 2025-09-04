package com.yuosef.section1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

    @GetMapping("/myContact")
    public String getContactDetails(){
        return "Here are the contact details";
    }
}
