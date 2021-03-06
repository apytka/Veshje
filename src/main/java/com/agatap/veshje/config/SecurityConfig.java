package com.agatap.veshje.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsAdapter userDetailsAdapter;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userDetailsAdapter);
        dap.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(dap);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/icon/*").permitAll()
                .antMatchers("/img/*").permitAll()
                .antMatchers("/img/icon/*").permitAll()
                .antMatchers("/img/dresses/*").permitAll()
                .antMatchers("/img/slideshow/*").permitAll()
                .antMatchers("/img/orders/*").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/products/all/search").permitAll()
                .antMatchers("/confirm-registration").permitAll()
                .antMatchers("/account-not-active").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/login/forgot-password").permitAll()
                .antMatchers("/login/forgot-password-newsletter").permitAll()
                .antMatchers("/login/reset-password**").permitAll()
                .antMatchers("/products/mini").permitAll()
                .antMatchers("/products/midi").permitAll()
                .antMatchers("/products/long").permitAll()
                .antMatchers("/products/new").permitAll()
                .antMatchers("/products/sale").permitAll()
                .antMatchers("/products/all").permitAll()
                .antMatchers("/products/*/filter").permitAll()
                .antMatchers("/products/*/filter/clear").permitAll()
                .antMatchers("/products/*/addNewsletter").permitAll()
                .antMatchers("/products/dress-details/**").permitAll()
                .antMatchers("/shopping-bag").permitAll()
                .antMatchers("/shopping-bag/**").permitAll()
                .antMatchers("/products/dress-details-newsletter/**").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/shop-at-veshje").permitAll()
                .antMatchers("/about-product").permitAll()
                .antMatchers("/gift-cards").permitAll()
                .antMatchers("/payment").permitAll()
                .antMatchers("/shipping").permitAll()
                .antMatchers("/exchange-and-returns").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/career").permitAll()
                .antMatchers("/privacy-policy").permitAll()
                .antMatchers("/terms-and-conditional").permitAll()
                .antMatchers("/contact/*").permitAll()
                .antMatchers("/shop-at-veshje/*").permitAll()
                .antMatchers("/about-product/*").permitAll()
                .antMatchers("/gift-cards/*").permitAll()
                .antMatchers("/payment/*").permitAll()
                .antMatchers("/shipping/*").permitAll()
                .antMatchers("/exchange-and-returns/*").permitAll()
                .antMatchers("/about/*").permitAll()
                .antMatchers("/career/*").permitAll()
                .antMatchers("/privacy-policy/*").permitAll()
                .antMatchers("/terms-and-conditional/*").permitAll()
                .antMatchers("/checkout/order/deliveries").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/account")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout();
    }
}
