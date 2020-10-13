package com.nnk.springboot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.context.annotation.Lazy;

/**
 * Java configuration for Spring Security
 */
@Configuration
@EnableWebSecurity
@ComponentScan
public class Config extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LogManager.getLogger(Config.class);

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationSuccessHandler successHandler;

    @Bean
    public AuthenticationSuccessHandler CustomSuccessHandler() {
        return new CustomSuccessHandler();
    }

    @Autowired
    public Config(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider())
                .inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterAfter(new LoginPageFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/login").permitAll().and().
                authorizeRequests().antMatchers(HttpMethod.GET, "/user/**", "/trade/**", "/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**").authenticated()
                .antMatchers(HttpMethod.POST, "/user/**", "/trade/**", "/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/user/**", "/trade/**", "/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/user/**", "/trade/**", "/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**").authenticated().and().
                requestCache().requestCache(new NullRequestCache()).and().
                cors().and().
                csrf().disable()
                .formLogin()
                .loginPage("/app/login")
                .failureUrl("/app/error")
                .successHandler(successHandler)
                .and()
                .logout()
                .logoutUrl("/app-logout").
                logoutSuccessUrl("/app/login");

    }

}
