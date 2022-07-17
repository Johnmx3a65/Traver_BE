package com.parovsky.traver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collections;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("ShopUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.PUT, "/reset-password").permitAll()
                .antMatchers(HttpMethod.POST, "/send-verification-code").permitAll()
                .antMatchers(HttpMethod.POST, "/check-verification-code").permitAll()
                .antMatchers(HttpMethod.GET, "/categories").permitAll()
                .antMatchers(HttpMethod.GET, "/category/**").permitAll()
                .antMatchers(HttpMethod.GET, "/locations").permitAll()
                .antMatchers(HttpMethod.GET, "/location/**").permitAll()
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedPage("/error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/categories").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(new ArrayList<>(Collections.singletonList("*")));
        configuration.setAllowedMethods(new ArrayList<>(Collections.singletonList("*")));
        configuration.setAllowedHeaders(new ArrayList<>(Collections.singletonList("*")));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
