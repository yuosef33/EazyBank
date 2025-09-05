package com.yuosef.section1.Controller;

import com.yuosef.section1.Constants.ApplicationConstants;
import com.yuosef.section1.Daos.CustomerDao;
import com.yuosef.section1.Models.Customer;
import com.yuosef.section1.Models.LoginRequestDto;
import com.yuosef.section1.Models.LoginResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final CustomerDao customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;
    private final Environment env;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(new Date(System.currentTimeMillis()));
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequest) {
        String jwt = "";
      /*  Authentication alreadyauthenticated= SecurityContextHolder.getContext().getAuthentication();   // this for checking if user is already authenticated then i resend the token
        if(alreadyauthenticated.isAuthenticated()){
            return ResponseEntity.ok()
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), request.getHeader("Authorization")));
        }*/
        Authentication authentication= UsernamePasswordAuthenticationToken.unauthenticated
                (loginRequest.email(),loginRequest.password());
       Authentication authenticationResponse= authenticationManager.authenticate(authentication);

       if (null != authenticationResponse && authenticationResponse.isAuthenticated()){
           if(null != env){
               String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY
                       ,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
               SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt= Jwts.builder().issuer("eazyBank").subject("JWT-TOKEN")
                       .claim("username",authenticationResponse.getName())
                       .claim("authorities",authenticationResponse.getAuthorities().stream().map(
                               GrantedAuthority::getAuthority
                       ).collect(Collectors.joining(",")))
                       .issuedAt(new Date())
                       .expiration(new Date((new Date()).getTime()+30000000))
                       .signWith(secretKey).compact();
           }
       }else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(HttpStatus.UNAUTHORIZED.getReasonPhrase(),"Invalid credentials"));
       }
        return ResponseEntity.ok().header(ApplicationConstants.JWT_HEADER,jwt)
                .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),jwt));

    }
}
