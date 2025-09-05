package com.yuosef.section1.Config.Filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

public class RequestValidationBeforeFilter implements Filter {
    // we here got AUTHORIZATION Header and decode it then got the email from him and check if it contains test
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
         String header = req.getHeader(HttpHeaders.AUTHORIZATION);
                         Enumeration<String> headerNames=   req.getHeaderNames();
             String path3 =     req.getRequestURI();
             String path1 =req.getContextPath();
            String path2 = req.getServletPath();
        if(null != header){
            header=header.trim();
            if(StringUtils.startsWithIgnoreCase(header,"Basic")){
               byte[] base64Token= header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    decoded= Base64.getDecoder().decode(base64Token);
                    String token=new String(decoded,StandardCharsets.UTF_8);
                    int delim= token.indexOf(":");
                    if(delim==-1){
                        throw new ServletException("Invalid Authorization header");
                    }
                    String email=token.substring(0,delim);
                    if(email.toLowerCase().contains("test")){
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }catch (IllegalArgumentException exception){
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }

            }
        }

        chain.doFilter(request,response);
    }

}
