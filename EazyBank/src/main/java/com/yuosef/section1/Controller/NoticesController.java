package com.yuosef.section1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
public class NoticesController {

    @GetMapping("/myNotices")
    public String getNoticesDetails(){
        return "Here are the notices details";
    }

}
