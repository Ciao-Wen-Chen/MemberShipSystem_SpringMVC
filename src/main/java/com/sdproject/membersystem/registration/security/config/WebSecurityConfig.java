package com.sdproject.membersystem.registration.security.config;

import com.sdproject.membersystem.filter.CustomAuthenticationFilter;
import com.sdproject.membersystem.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig{

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().anyRequest().permitAll();

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new CustomAuthenticationFilter(authenticationManager));
//        http.authenticationManager(new CustomAuthenticationFilter());
        return http.build();
//        http
//                .csrf().disable()
//                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .authorizeRequests()
//                    .antMatchers("/api/**")
//                    .permitAll()
//                .anyRequest().authenticated().and().formLogin();
//        return http.build();
    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
    @Autowired
    void registerProvider(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(new CustomAuthenticationFilter(authenticationManager));
    }
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManagerBuilder builder) {
        return new CustomAuthenticationFilter(builder);
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=
                    new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(memberService);
        return provider;
    }

}
