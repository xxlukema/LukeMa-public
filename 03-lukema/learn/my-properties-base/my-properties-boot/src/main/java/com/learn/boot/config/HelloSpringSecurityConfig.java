package com.learn.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.log4j.Log4j2;

import static org.springframework.security.config.Customizer.withDefaults;


/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 */
@Log4j2
@Configuration
@EnableWebSecurity
public class HelloSpringSecurityConfig {

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    // @formatter:off

    /*
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        log.debug(() -> "Called.");

        return (web) -> web.ignoring()
                .requestMatchers("/css/**")
                .requestMatchers("/js/**")
                .requestMatchers("/favicon.ico")
                .requestMatchers("/image/**");
    }
    */

    /**
     * HttpSecurity implements formLogin() and logout() methods.
     * When POST actions /login or /logout is called, HttpSecurity.formLogin() or
     * HttpSecurity.logout() will be invoked.
     *
     * roles ADMIN allow to access /admin/**
     * roles USER allow to access /user/**
     * custom 403 access denied handler
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.debug(() -> "Called.");

        /**
         * CSFR is enbaled ob spring by default.
         *
         * http.csrf().disable();
         */
        // httpSecurity.csrf().disable()
        httpSecurity.csrf(csrf -> csrf
                // .csrfTokenRepository(jwtCsrfTokenRepository)
                // .ignoringAntMatchers(ignoreCsrfAntMatchers)
                .disable())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/actuator/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/swagger-ui/**").permitAll())
                // .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/v2/api-docs/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/v3/api-docs/**").permitAll())
                /**
                 * This maps all directories under src/main/resources/static:
                 */
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/css/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/js/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/favicon.ico").permitAll())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/image/**").permitAll())
                // .authorizeHttpRequests().anyRequest().authenticated()
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic(withDefaults());

        httpSecurity.formLogin(login -> login
                .loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .loginProcessingUrl("/login")
                .failureUrl("/login")
                .permitAll())
                .logout(logout -> logout // This defines the /logout action
                        .permitAll());

        /**
         * Mandatory!! Without this line, security will mess up.
         */
        // httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public  AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration)
            throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("admin")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
