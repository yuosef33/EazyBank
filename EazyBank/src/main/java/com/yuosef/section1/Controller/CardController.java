package com.yuosef.section1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {

    @GetMapping("/mycard")
    public String getCardDetails(){
        return "Here are the card details";
    }
}
