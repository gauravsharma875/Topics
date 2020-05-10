package com.example.UserApp.security;

import com.example.UserApp.service.UserService;
import com.example.UserApp.service.UserServiceImpl;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;





    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        http.authorizeRequests().antMatchers("/users/*").hasIpAddress(environment.getProperty("gateway.ip"))
        .and()
        .addFilter(getAuthenticationFilter());
        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter=new AuthenticationFilter(userService, environment,
                authenticationManager());
//        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
