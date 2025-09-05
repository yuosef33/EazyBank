package com.yuosef.section1.Config.Filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderThreadLocalAccessor;

import java.io.IOException;

@Slf4j
public class AuthoritiesLoggingAfterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       Authentication authentication=  SecurityContextHolder.getContext().getAuthentication();
       if (null !=authentication){
           log.info("User {} is authenticated with authorities {}",authentication.getName(),authentication.getAuthorities()+
                   "\n credentials :"+ authentication.getCredentials() +"\n principal :"+ authentication.getPrincipal());
       }
       chain.doFilter(request,response);
    }
}
