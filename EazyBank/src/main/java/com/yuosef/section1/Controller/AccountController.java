package com.yuosef.section1.Controller;

import com.yuosef.section1.Daos.AccountDao;
import com.yuosef.section1.Daos.CustomerDao;
import com.yuosef.section1.Models.Account;
import com.yuosef.section1.Models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountDao accountsRepository;
    private final CustomerDao customerRepositor;

    @GetMapping("/myAccount")
    public Account getAccountDetails(@RequestParam String email) {
        Optional<Customer>customer= customerRepositor.findByEmail(email);
        if(customer.isPresent()) {
            Account accounts = accountsRepository.findByCustomerId(customer.get().getId());

            if (accounts != null) {
                return accounts;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
            }
        }
        else{
            return null;
        }
    }
}
