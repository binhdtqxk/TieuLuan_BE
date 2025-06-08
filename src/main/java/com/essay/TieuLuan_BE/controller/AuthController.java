package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.config.JwtProvider;
import com.essay.TieuLuan_BE.entity.Role;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.entity.Verification;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.repository.UserRepository;
import com.essay.TieuLuan_BE.response.AuthResponse;
import com.essay.TieuLuan_BE.service.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetails;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaProperties kafkaProperties;

    @PostMapping("/signup") //Create new user
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

        System.out.println("user, " + user);
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String birthDate = user.getBirthDate();
        if(checkEmail(email)){
            throw new UserException("Email is already in use");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        createdUser.setBirthDate(birthDate);
        createdUser.setVerification(new Verification());
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        userRole.setId(1);
        createdUser.setRole(userRole);
        createdUser.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(createdUser);
        //Create auth spring security first and send it to SecurityContextHolder to handle it.
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), password, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        //Create jwt token and send back to client
        String token = jwtProvider.generateToken(auth);

        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin") //Đăng nhập
    public ResponseEntity<AuthResponse> signIn(@RequestBody User user) throws UserException {
        String username = user.getEmail();
        String password = user.getPassword();
        //Validate the username and password then return to spring security auth to create jwt token
        Authentication authentication = authenticate(username, password);
        //Tạo jwt token
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, true);
        //Return token to client for validate further
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username of password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    @PostMapping("/sendVerificationCode")
    public ResponseEntity<String> sendVerificationCode(@RequestBody String email) {
        System.out.println("nhan dc request");
        System.out.println("Kafka consumer config: " + kafkaProperties.getConsumer());
        System.out.println("Kafka bootstrap servers: " + kafkaProperties.getBootstrapServers());
        // Generate a random code
        String code = generateRandomCode();

        // Send the code to Kafka
        kafkaTemplate.send("verificationCodeTopic", email, code);

        return ResponseEntity.ok(code);
    }
    @PostMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestBody String email) {
        boolean res=checkEmail(email);
        return ResponseEntity.ok(res);
    }

    private String generateRandomCode() {
        return String.valueOf((int) (Math.random() * 1000000));
    }
    @PostMapping("/sendNewPassword")
    public ResponseEntity<HttpStatus> sendNewPassword(@RequestParam String email) {
        String code = generateRandomPassword();
        kafkaTemplate.send("newPasswordTopic", email, code);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    private String generateRandomPassword() {
        return String.valueOf((int) (Math.random() * 100000000));
    }
    private boolean checkEmail(String email){
        boolean res = userRepository.findByEmail(email) != null;
        return res;
    }
}
