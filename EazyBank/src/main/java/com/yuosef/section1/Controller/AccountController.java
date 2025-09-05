package com.yuosef.section1.Controller;

import com.yuosef.section1.Daos.AccountDao;
import com.yuosef.section1.Models.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountDao accountsRepository;

    @GetMapping("/myAccount")
    public Account getAccountDetails(@RequestParam long id) {
        Account accounts = accountsRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }
    }
}
