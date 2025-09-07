package com.yuosef.section1.Controller;

import com.yuosef.section1.Daos.CustomerDao;
import com.yuosef.section1.Daos.LoansDao;
import com.yuosef.section1.Models.Customer;
import com.yuosef.section1.Models.Loans;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoansController {
    private final LoansDao loanRepository;
    private final CustomerDao customerRepositor;

    @GetMapping("/myLoans")
   // @PostAuthorize("hasRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        Optional<Customer> customer= customerRepositor.findByEmail(email);
    if(customer.isPresent()) {
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.get().getId());
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }
    else return null;
    }
}
