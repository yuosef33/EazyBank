package com.yuosef.section1.Events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthentcationEvents {
    @EventListener
    public void onSucces (AuthenticationSuccessEvent successEvent){
            log.info("Authentication Success",successEvent.getAuthentication().getName());
        }
    @EventListener
    public void onFailure (AbstractAuthenticationFailureEvent failureEvent){
        log.info("Authentication Success",failureEvent.getAuthentication().getName());
        failureEvent.getException().getMessage();
    }
}
