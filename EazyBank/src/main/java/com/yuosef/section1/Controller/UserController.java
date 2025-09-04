package com.yuosef.section1.Controller;

import com.yuosef.section1.Daos.CustomerDao;
import com.yuosef.section1.Models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final CustomerDao repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer){
        try {
            String hashedPassword = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashedPassword);
          Customer savedcustomer = repository.save(customer);
          if(savedcustomer != null){
              return ResponseEntity.status(HttpStatus.CREATED).body("User "+ customer.getEmail()+" registered successfully");
          }else {
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User "+ customer.getEmail()+" registration failed");
          }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred:"+e.getMessage());
        }

    }
}
