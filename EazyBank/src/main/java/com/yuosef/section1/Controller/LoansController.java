package com.yuosef.section1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
public class LoansController {

    @GetMapping("/myLoans")
    public String getloansDetails(){
        return "Here are the loans details";
    }
}
