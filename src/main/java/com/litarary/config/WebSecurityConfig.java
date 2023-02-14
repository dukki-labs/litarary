package com.litarary.config;

import com.litarary.config.filter.FilterExceptionHandler;
import com.litarary.config.filter.JwtAuthenticationFilter;
import com.litarary.utils.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final FilterExceptionHandler filterExceptionHandler;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 정적파일에 대해서는 Spring Security 설정을 하지 않도록 설정.(Api문서 때문)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf().ignoringAntMatchers("/h2-console/**").disable()
                .headers(headers -> headers.frameOptions().disable())
                .httpBasic().disable()
                .authorizeHttpRequests(auth ->
                        auth.antMatchers("/api/v1/account/**", "/").permitAll() // 해당 요청은 권한이 없어도 요청가능하다.
                                .antMatchers("/h2-console/**").permitAll()
                                .antMatchers("/docs/**").permitAll()
                                .antMatchers("static/docs/**").permitAll()
                                .anyRequest().authenticated() // 이외 모든 요청은 권한이 있어야 한다.
                                .and()
                                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(filterExceptionHandler, JwtAuthenticationFilter.class)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //JWT토큰을 사용할 것임으로 세션적용하지 않도록 설정
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
