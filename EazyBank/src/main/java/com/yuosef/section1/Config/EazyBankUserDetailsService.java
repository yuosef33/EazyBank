package com.yuosef.section1.Config;

import com.yuosef.section1.Daos.CustomerDao;
import com.yuosef.section1.Models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EazyBankUserDetailsService implements UserDetailsService { // the main method we need to send the correct user to the security config
    private final CustomerDao customerDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer customer =  customerDao.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user details not found for " + username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+customer.getRole()));
        return new User(customer.getEmail(), customer.getPwd(),authorities);
    }
}
