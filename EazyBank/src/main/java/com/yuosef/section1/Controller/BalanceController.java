package com.yuosef.section1.Controller;

import com.yuosef.section1.Daos.AccountTransactionsDao;
import com.yuosef.section1.Daos.CustomerDao;
import com.yuosef.section1.Models.AccountTransactions;
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
public class BalanceController {

    private final AccountTransactionsDao accountTransactionsRepository;
    private final CustomerDao customerRepositor;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        Optional<Customer> customer= customerRepositor.findByEmail(email);
        if(customer.isPresent()){
        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                findByCustomerIdOrderByTransactionDtDesc(customer.get().getId());
        if (accountTransactions != null) {
            return accountTransactions;
        } else {
            return null;
        }
    }else return null;

    }
}
