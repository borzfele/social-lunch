package com.peanuts.sociallunch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        TODO create html for these
//        http.sessionManagement()
//                .expiredUrl("/sessionExpired.html")
//                .invalidSessionUrl("/invalidSession.html");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth//.jdbcAuthentication().authoritiesByUsernameQuery();
//                .inMemoryAuthentication()
//                .withUser("otto1").password("otto1").roles("USER");
//
//        auth//.jdbcAuthentication().authoritiesByUsernameQuery();
//                .inMemoryAuthentication()
//                .withUser("otto2").password("otto2").roles("USER");

        auth.userDetailsService(userDetailsService);
    }
}