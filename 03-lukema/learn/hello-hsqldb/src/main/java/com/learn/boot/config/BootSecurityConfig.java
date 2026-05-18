package com.learn.boot.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.learn.filter.OncePerRequestMustHaveLowestPrecedenceFilter8;

import lombok.extern.log4j.Log4j2;


/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 */
@Configuration
@Log4j2
public class BootSecurityConfig {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private javax.sql.DataSource dataSource;

    @Autowired
    @Qualifier("springSecurityUserDetailsService")
    private UserDetailsService userDetailsService;

    /**
     * Useful to authenticate against a third party.
     */
    // @Autowired
    // @Qualifier("jwtAuthenticationProvider")
    // private AuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private OncePerRequestMustHaveLowestPrecedenceFilter8 oncePerRequestFilter;

    // @formatter:off

    protected String[] ignoreCsrfAntMatchers = {
            "/dynamic-builder-compress",
            "/dynamic-builder-general",
            "/dynamic-builder-specific",
            "/set-secrets"
    };

    /**
     * HttpSecurity implements formLogin() and logout() methods.
     * When POST actions /login or /logout is called, HttpSecurity.formLogin() or HttpSecurity.logout() will be invoked.
     *
     * roles ADMIN allow to access /admin/**
     * roles USER allow to access /user/**
     * custom 403 access denied handler
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        log.debug(() -> "Called.");

        /**
         * CSFR is enbaled on spring by default.
         *
         * "https://docs.spring.io/spring-security/site/docs/5.2.x/reference/html/features.html#csrf-when"
         * - When should you use CSRF protection? Our recommendation is to use CSRF protection for any request that could be processed
         *   by a browser by normal users. If you are only creating a service that is used by non-browser clients, you will likely want
         *   to disable CSRF protection.
         */
        httpSecurity.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                    /** Start for ??? */
                    authorize.requestMatchers("/css/**").permitAll();
                    authorize.requestMatchers("/js/**").permitAll();
                    authorize.requestMatchers("/webjars/**").permitAll(); /** For sawgger */
                    authorize.requestMatchers("/assets/**").permitAll();
                    authorize.requestMatchers("/image/**").permitAll();
                    /** Start spring security */
                    authorize.requestMatchers("/", "/home", "/about", "/formvalidation", "/datatable", "/greeting").permitAll();
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers("/actuator/**").permitAll();
                    authorize.requestMatchers("/error/**").permitAll();  /** handle global errors */
                    authorize.requestMatchers("/spring/**").permitAll();  /** by pass securities */
                    authorize.requestMatchers("/content/**").permitAll();  /** by pass securities */
                    authorize.requestMatchers("/swagger-ui/**").permitAll();  /** for Swagger 2, 3, and OpenAPI */
                    authorize.requestMatchers("/swagger-resources/**").permitAll();  /** for Swagger */
                    authorize.requestMatchers("/v3/api-docs/**").permitAll();  /** for Swagger OpenAPI */
                    authorize.requestMatchers("/property/**", "/ping", "/props", "/representative/**").permitAll();
                    authorize.requestMatchers("/admin/**").hasRole("ADMIN");
                    authorize.requestMatchers("/rest/**").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers("/user/**").hasAnyRole("USER");
                    authorize.anyRequest().authenticated();
                })
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
                        .permitAll())
                .exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler));

        httpSecurity.addFilterAfter(oncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);

        /**
         * Mandatory!! Without this line, security will mess up.
         */
        // httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }


    /**
     * The annotation @Autowired does not really care which method name you use. So, a method name like prepare
     * works just as well as a method name along the lines of setMovieCatalog.
     *
     * Furthermore, Spring handles multiple arguments in the method with @Autowired as well. This is typically
     * used for constructor based injection but works out just fine for other methods (like your prepare-method).
     * So, what is required to make this work? Well, first of all the arguments to the method must be beans that
     * are known by the Spring context. This means that the beans must be wired in the XML-context, annotated
     * with a @Component, or a @Bean from a @Configuration class. Secondly, the class that holds the @Autowired
     * method must also be a bean that is known to the Spring context.
     *
     * If both of the above are fulfilled, the @Autowired simply works as expected. It can be used on any instance
     * method no matter the name.
     * @throws Exception
     *
     * @Autowired with Config methods:
     *
     * Config methods may have an arbitrary name and any number of arguments;
     * each of those arguments will be autowired with a matching bean in the Spring container.
     *
     * The default PasswordEncoder is built as a DelegatingPasswordEncoder.
     *
     * You can also simply prefix {noop} to your passwords in order for the
     * DelegatingPasswordEncoder use the NoOpPasswordEncoder to validate these passwords.
     * Notice that NoOpPasswordEncoder is deprecated.
     *
     */

     // @formatter:on
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {

        final String IN_MEMORY = "inMemory";
        final String User_Details_Service = "UserDetailsService";
        final String JDBC = "JDBC";

        final String authMethods[] = { IN_MEMORY, User_Details_Service, JDBC };

        // int index = Arrays.asList(authMethods).indexOf(IN_MEMORY);
        // int index = Arrays.asList(authMethods).indexOf(User_Details_Service);
        int index = Arrays.asList(authMethods).indexOf(JDBC);
        // int index = Arrays.asList(authMethods).indexOf(ThirdParty_AuthenticationProvider);
        // NOT Working int index = Arrays.asList(authMethods).indexOf(JWT_AuthenticationManager);

        final String authMethod = authMethods[index];

        switch (authMethod) {
            /**
             * Using credentials added to in memory database added in the following code block.
             */

            case IN_MEMORY -> {
                log.debug(() -> "====== Called at start up. This is called at boot start up to load PasswordEncoder into memory. ======");

                final boolean ENCODE_CHANGEME = true;

                if (ENCODE_CHANGEME) {
                    auth.inMemoryAuthentication()
                            .withUser("user").password(passwordEncoder.encode("user")).roles("USER")
                            .and()
                            .withUser("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN");

                    log.info(() -> "------------- Passwords are encoded. -------------");
                } else {
                    auth.inMemoryAuthentication()
                            .withUser("user").password("{noop}user").roles("USER")
                            .and()
                            .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");

                    log.info(() -> "------------- Passwords are not encoded. -------------");
                }
            }

            /**
             * Using credentials saved in spring_security_user and spring_security_authority tables in postgres.
             */

            case User_Details_Service -> auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

            /**
             * Using credentials saved in spring_security_user and spring_security_authority tables in postgres.
             */
            case JDBC -> auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder)
                    .usersByUsernameQuery(
                            "select username, password, enabled from spring_security_user where username = ? and enabled = true")
                    .authoritiesByUsernameQuery("select username, authority from spring_security_authority where username = ? ");

            default -> {
            }
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration)
        throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    // @formatter:on
}
