package com.example.UserApp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurity extends WebSecurityConfigurerAdapter {


    private Environment environment;

    @Value(value = "gateway.ip")
    private String zuul;

    @Autowired
    public WebSecurity(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("zuul :: "+ zuul);

        http.csrf().disable();
        //http.authorizeRequests().antMatchers("/users/*").permitAll();
        http.authorizeRequests().antMatchers("/users/*").hasIpAddress(environment.getProperty("gateway.ip"));
        http.headers().frameOptions().disable();
    }
}
