package com.yuosef.section1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

    @GetMapping("/myBalance")
    public String getbalanceDetails(){
        return "Here are the Balance details";
    }
}
