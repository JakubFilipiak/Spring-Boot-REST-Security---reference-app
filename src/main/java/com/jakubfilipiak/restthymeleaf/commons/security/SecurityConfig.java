package com.jakubfilipiak.restthymeleaf.commons.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Jakub Filipiak on 09.05.2019.
 */
@Configuration
@EnableWebSecurity
// prePostEnabled - nadajemy role bezpośrednio przy metodach
// securedEnable - nadajemy role w pliku security
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private CustomUserService customUserService;
    private PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserService customUserService, PasswordEncoder passwordEncoder) {
        this.customUserService = customUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable();

        http
                .authorizeRequests()
                .antMatchers("/login**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/signin")
                .usernameParameter("username")
                .passwordParameter("password")

//                .defaultSuccessUrl("/address") - można też użyć tego, ale..
//                nie będzie można zweryfikować jaką rolę ma zalogowany user
//                tylko po prostu przekierowanie po pomyślnym zalogowaniu
                .successHandler((req, res, auth) -> {
                    for (GrantedAuthority authority : auth.getAuthorities()) {
                        System.out.println(authority.getAuthority());
                    }
                    res.sendRedirect("/"); //home page
                })
                .failureHandler((req, res, exp) -> {
                    String errorMessage;
                    if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
                        errorMessage = "Invalid username or password";
                    } else {
                        errorMessage = "unknown error " + exp.getMessage();
                    }
                    req.getSession().setAttribute("message", errorMessage);
                    res.sendRedirect("/login");
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.sendRedirect("/login");
                })
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login") // status=403
                .and()
                .csrf().disable();

//        http.headers().frameOptions().disable(); // w przypadku używania bazy H2
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserService)
                .passwordEncoder(passwordEncoder);
    }


}
