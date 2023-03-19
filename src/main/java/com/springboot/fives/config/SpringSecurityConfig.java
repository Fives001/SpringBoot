package com.springboot.fives.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

// import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import jakarta.servlet.DispatcherType;

// @Configuration
// @EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        System.out.println("==================PathRequest.toH2Console():"+PathRequest.toH2Console());
        http
                .csrf().disable().cors().disable()
                .authorizeHttpRequests(request -> request
                	.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/js/**", "/images/**", "/css/**","/h2-console/**").permitAll()
                        .requestMatchers("/member/**").permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요
                //.anyRequest().permitAll()	// 어떠한 요청이라도 인증필요                
                )
                .formLogin(login -> login	// form 방식 로그인 사용
                        .defaultSuccessUrl("/list/s", true)	// 성공 시 dashboard로
                        .permitAll()	// 대시보드 이동이 막히면 안되므로 얘는 허용
                )
                .logout(withDefaults()
                );	// 로그아웃은 기본설정으로 (/logout으로 인증해제)
        return http.build();
    }
}