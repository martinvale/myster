package com.ibiscus.myster.configuration;

import com.ibiscus.myster.repository.security.UserRepository;
import com.ibiscus.myster.service.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/shopper/**").hasRole("SHOPPER")
                .and().formLogin().loginPage("/login").successHandler(getSuccessHandler())
                .and().rememberMe().alwaysRemember(true)
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    private AuthenticationSuccessHandler getSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/shopper/assignments/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("martinvalletta@gmail.com").password("test").roles("SHOPPER");
        //auth.userDetailsService(getUserService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new UserService(userRepository);
    }

    /*@Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("martinvalletta@gmail.com")
                        .password("test")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(newArrayList(user));
    }*/
}
