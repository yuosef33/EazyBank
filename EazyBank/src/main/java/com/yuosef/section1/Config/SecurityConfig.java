package com.yuosef.section1.Config;

import com.yuosef.section1.ExceptionHandling.CustomAccessDeniedHandler;
import com.yuosef.section1.ExceptionHandling.CustomBasicAuthenticationEntryPorint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(sm -> sm.invalidSessionUrl("/invalid-session").maximumSessions(1).maxSessionsPreventsLogin(true))
                .requiresChannel((rcc)-> rcc.anyRequest().requiresInsecure())
                .authorizeHttpRequests(
                (Request) -> Request.requestMatchers("/api/v1/notices/**","/api/v1/contact/**","api/v1/user/register","/error/**","/invalid-session").permitAll()
                .requestMatchers("/api/v1/loans/**","/api/v1/card/**","/api/v1/account/**","/api/v1/balance/**")
                        .authenticated()

        );
        http.csrf(csrf -> csrf.disable());
        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPorint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }


//    @Bean
//    public UserDetailsService userDetailService(DataSource datasource){
//         return new JdbcUserDetailsManager(datasource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
}
