package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.config.JwtProvider;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.entity.Varification;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.repository.UserRepository;
import com.essay.TieuLuan_BE.response.AuthResponse;
import com.essay.TieuLuan_BE.service.impl.CustomUserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private CustomUserDetailsServiceImplementation customUserDetails;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException {

        System.out.println("user, "+ user);
        String email=user.getEmail();
        String password=user.getPassword();
        String fullName=user.getFullName();
        String birthDate=user.getBirthDate();

        User isEmailExist=userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is already used with another account");
        }

        User createdUser= new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        createdUser.setBirthDate(birthDate);
        createdUser.setVerification(new Varification());

        User savedUser=userRepository.save(createdUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtProvider.generateToken(auth);

        AuthResponse authResponse = new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody User user)throws UserException {
        String username=user.getEmail();
        String password=user.getPassword();

        Authentication authentication=authenticate(username,password);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
    }
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username of password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
