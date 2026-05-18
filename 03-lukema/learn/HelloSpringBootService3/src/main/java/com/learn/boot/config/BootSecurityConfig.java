package com.learn.boot.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
import org.springframework.security.web.csrf.CsrfTokenRepository;

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
    @Qualifier("thirdPartyAuthenticationProvider")
    private AuthenticationProvider thirdPartyAuthenticationProvider;

    @Autowired
    CsrfTokenRepository jwtCsrfTokenRepository;

    @Autowired
    private OncePerRequestMustHaveLowestPrecedenceFilter8 oncePerRequestFilter;

    // @formatter:off

    protected String[] ignoreCsrfAntMatchers = {
            "/dynamic-builder-compress",
            "/dynamic-builder-general",
            "/dynamic-builder-specific",
            "/set-secrets"
    };

    /*
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        log.debug(() -> "Called.");

        // Spring Security should completely ignore URLs starting with /resources/. This maps all directories under src/main/resources/static.
        return (web) -> web.ignoring()
                                      .requestMatchers("/css/**")
                                      .requestMatchers("/js/**")
                                      .requestMatchers("/webjars/**") // For sawgger
                                      .requestMatchers("/favicon.ico")
                                      .requestMatchers("/image/**");
    }
    */

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
         *
         * http.csrf().disable(); <=== spring boot 2.7
         * httpSecurity.csrf(csrf -> csrf.disable()); <=== spring boot 3.x
         * httpSecurity.csrf(withDefaults()) <=== spring boot 3.x
         *
         * httpSecurity
         *     .csrf(csrf ->
         *         csrf
         *             .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
         *     );
         */
        httpSecurity.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                // .csrfTokenRepository(jwtCsrfTokenRepository)
                // .ignoringAntMatchers(ignoreCsrfAntMatchers)
                .authorizeHttpRequests((authorize) -> {
                    /** Start for ??? */
                    authorize.requestMatchers("/css/**").permitAll();
                    authorize.requestMatchers("/js/**").permitAll();
                    authorize.requestMatchers("/webjars/**").permitAll(); /** For sawgger */
                    authorize.requestMatchers("/assets/**").permitAll();
                    authorize.requestMatchers("/image/**").permitAll();
                    /** Start spring security */
                    authorize.requestMatchers("/", "/home", "/about", "/formvalidation", "/datatable", "/greeting").permitAll();
                    authorize.requestMatchers("/AngularJS/**").permitAll();
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers("/jwt/login").permitAll();
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
                    // authorize.requestMatchers("/swagger-ui.html").permitAll();  /** for Swagger 2 */
                    // authorize.requestMatchers("/jwt/**").permitAll();
                    // authorize.requestMatchers("/v2/api-docs/**").permitAll();  /** for Swagger 2 */
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
        final String ThirdParty_AuthenticationProvider = "ThirdParty_AuthenticationProvider";
        final String JWT_AuthenticationManager = "JWT_AuthenticationManager";

        final String authMethods[] = { IN_MEMORY, User_Details_Service, JDBC, ThirdParty_AuthenticationProvider, JWT_AuthenticationManager };

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

            /**
             * Using credentials saved in thirdparty package
             *
             * curl -k -i -u "admin:admin" -X GET 'https://localhost:8443/rest/security/ping' -H 'Accept: application/json'
             */
            case ThirdParty_AuthenticationProvider -> auth.authenticationProvider(thirdPartyAuthenticationProvider);

            default -> {
            }
        }

        /**
         * JSON Web Tokens (JWTs, pronounced "jots")
         *
         * Using JWT_AuthenticationManager
         *
         * curl -k -i -u "admin:admin" -X GET 'https://localhost:8443/rest/security/ping' -H 'Accept: application/json' -H "Authorization: Bearer weeddd"
         *
         * Not Working
         *
         * https://www.youtube.com/watch?v=X80nJ5T7YpE
         *
         */
        // case JWT_AuthenticationManager:
        // auth.parentAuthenticationManager(jwtAuthenticationProvider);
        //    auth.authenticationProvider(jwtAuthenticationProvider);
        //     break;
        /**
         * API Key
         */
        /**
         * Bear token
         */
        /**
         * OAuth 2
         *
         * https://www.youtube.com/watch?v=X80nJ5T7YpE
         *
         */
        /**
         * Spring <https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/csrf.html>
         * Cross Site Request Forgery (CSRF)
         * <input name="_csrf" type="hidden" value="f3f42ea9-3104-4d13-84c0-7bcb68202f16"/>
         */
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration)
        throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    // @formatter:on

    /**
     * Good for InMemoryUser
     */
    /*
    @Bean
    UserDetailsService users() {
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
    */
}
