package com.example.project_eaut_1.Config;

import com.example.project_eaut_1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final DataSource dataSource;

    @Lazy
    @Autowired // Constructor injection
    public SecurityConfig(UserService userService, DataSource dataSource) {
        this.userService = userService;
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/Admin/**").hasRole("ADMIN")
                                .requestMatchers("/User/**").permitAll()
                                .requestMatchers("/user/register").permitAll()
                                .requestMatchers("/admin/registerAdmin").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticate")
                                .defaultSuccessUrl("/index")
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/index")
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
