package com.yuosef.section1.Controller;

import com.yuosef.section1.Daos.CardsDao;
import com.yuosef.section1.Daos.CustomerDao;
import com.yuosef.section1.Models.Cards;
import com.yuosef.section1.Models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CustomerDao customerRepositor;
    private final CardsDao cardsRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        Optional<Customer> customer= customerRepositor.findByEmail(email);
        if(customer.isPresent()){
        List<Cards> cards = cardsRepository.findByCustomerId(customer.get().getId());
        if (cards != null ) {
            return cards;
        }else {
            return null;
        }
    }
        else return null ;
    }


}
