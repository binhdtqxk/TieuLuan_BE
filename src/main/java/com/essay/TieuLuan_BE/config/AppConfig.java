package com.essay.TieuLuan_BE.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtProvider jwtProvider) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigrationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().permitAll()
                ).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);
//        http.formLogin(withDefaults());
//        http.httpBasic(withDefaults());
        return http.build();
    }
//    private OidcUserService oidcUserService(JwtProvider jwtProvider) {
//        OidcUserService oidcUserService = new OidcUserService();
//        oidcUserService.setOauth2UserService((OAuth2UserRequest userRequest) -> {
//            OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//            String email = oauth2User.getAttribute("email");
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    email,
//                    null,
//                    Collections.singletonList(() -> "ROLE_USER")
//            );
//            String jwt = jwtProvider.generateToken(authentication);
//            // Lưu JWT vào response header
//            return new DefaultOidcUser(
//                    authentication.getAuthorities(),
//                    userRequest.getIdToken(),
//                    oauth2User.getAttributes(),
//                    "sub"
//            );
//        });
//        return oidcUserService;
//    }
    private CorsConfigurationSource corsConfigrationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
