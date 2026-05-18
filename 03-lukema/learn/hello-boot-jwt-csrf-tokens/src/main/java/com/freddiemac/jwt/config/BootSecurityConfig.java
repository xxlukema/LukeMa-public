package com.freddiemac.jwt.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.EhCacheBasedUserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.freddiemac.jwt.encode.MyPasswordEnconder;

import lombok.extern.log4j.Log4j2;


/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 */
@Log4j2
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE + 1000)
public class BootSecurityConfig
    extends WebSecurityConfigurerAdapter {

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
    CsrfTokenRepository jwtCsrfTokenRepository;

    // @formatter:off
    
    @Override
    public void configure(WebSecurity web) {

        log.debug(() -> "Called.");
        
        /**
         *  Spring Security should completely ignore URLs starting with /resources/
         *  
         *  This maps all directories under src/main/resources/static
         */
        web.ignoring()
                      .antMatchers("/css/**")
                      .antMatchers("/js/**")
                      .antMatchers("/webjars/**") /** For sawgger */
                      .antMatchers("/favicon.ico")
                      .antMatchers("/image/**");
    }

    /**
     * HttpSecurity implements formLogin() and logout() methods. 
     * When POST actions /login or /logout is called, HttpSecurity.formLogin() or HttpSecurity.logout() will be invoked.
     *  
     * roles ADMIN allow to access /admin/**
     * roles USER allow to access /user/**
     * custom 403 access denied handler
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        log.debug(() -> "Called.");
        
        /**
         * CSFR is enbaled ob spring by default.
         * 
         * http.csrf().disable();
         */
        httpSecurity.csrf().disable();
        
        httpSecurity
                    /**
                     * This is important! It specifies which antMatchers are intended for this configuration.
                     * It makes this configuration different from others: Different URL coverage.
                     *     .requestMatchers().antMatchers(...)
                     */
                    .requestMatchers()
                    .antMatchers("/spring/**",
                                 "/ping/**",
                                 "/actuator/**",
                                 "/swagger-ui.html",
                                 "/swagger-ui/**",
                                 "/swagger-resources/**",
                                 "/v3/**",
                                 "/admin/**",
                                 "/rest/**",
                                 "/user/**")
                    .and()
                    .authorizeRequests()
                    // .antMatchers("/jwtlogin").permitAll()
                    // .antMatchers("/jwt/**").hasAnyRole("ADMIN")
                    .antMatchers("/spring/**").permitAll() /** by pass securities */
                    .antMatchers("/ping/get").permitAll()
                    .antMatchers("/ping/**").authenticated()
                    .antMatchers("/actuator/**").permitAll()
                    .antMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll() /** for Swagger */
                    .antMatchers("/swagger-resources/**").permitAll() /** for Swagger */
                    .antMatchers("/v3/**").permitAll() /** for Swagger */
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .antMatchers("/rest/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/user/**").hasAnyRole("USER")
                    // .anyRequest().authenticated()
                    // .anyRequest().denyAll()
                    .and()
                    /**
                     * Make it session stateless for RESTful:
                     */
                    // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .sessionManagement().disable()
                    // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                    /**
                     * This will trigger basic authentication when it sees 'curl -u "admin:admin" ...':
                     */
                    .httpBasic();
                    // .oauth2Login();
        
        httpSecurity.formLogin()
                     .loginPage("/login")
                        .usernameParameter("username").passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .loginProcessingUrl("/login")
                        .failureUrl("/login")
                        .permitAll()
                     .and()
                     .logout() // This defines the /logout action
                        .permitAll()
                     .and()
                     .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        
        /**
         * Mandatory!! Without this line, security will mess up.
         */
        // httpSecurity.csrf().disable();
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
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        final String IN_MEMORY = "inMemory";
        final String User_Details_Service = "UserDetailsService";
        final String JDBC = "JDBC";
        final String ThirdParty_AuthenticationProvider = "ThirdParty_AuthenticationProvider";
        final String JWT_AuthenticationManager = "JWT_AuthenticationManager";
        
        final String authMethods [] = {IN_MEMORY, User_Details_Service, JDBC, ThirdParty_AuthenticationProvider, JWT_AuthenticationManager};

        
        // int index = Arrays.asList(authMethods).indexOf(IN_MEMORY);
        int index = Arrays.asList(authMethods).indexOf(User_Details_Service);
        // int index = Arrays.asList(authMethods).indexOf(JDBC);
        
        final String authMethod = authMethods[index];
        
        switch (authMethod ) {
            
            /**
             * Using credentials added to in memory database added in the following code block.
             */
            case IN_MEMORY:
                log.debug(() -> "====== Called at start up. This is called at boot start up to load PasswordEncoder into memory. ======");
                
                final boolean ENCODE_CHANGEME = true;
                
                if(ENCODE_CHANGEME) {
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
                
                break;
            
            /**
              * Using credentials saved in spring_security_user and spring_security_authority tables in postgres. 
              */
            case User_Details_Service:
                 
                 auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
                 
                 break;
                 
            /**
              * Using credentials saved in spring_security_user and spring_security_authority tables in postgres. 
              */ 
            case JDBC: 
                 auth.jdbcAuthentication()
                     .dataSource(dataSource)
                     .passwordEncoder(passwordEncoder)
                     .usersByUsernameQuery("select username, password, enabled from spring_security_user where username = ? and enabled = true")
                     .authoritiesByUsernameQuery("select username, authority from spring_security_authority where username = ? ");
                 
                 break;
            
            default:
                break;
        }
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
       
       boolean useMyPasswordEnconder = true;
       
       if(useMyPasswordEnconder) {
           return new MyPasswordEnconder();
       } else {
           return new BCryptPasswordEncoder();
       }
    }
    
    @Bean
    UserCache digestUserCache() throws Exception {
        
        /**
         * Unable to use EhCacheBasedUserCache
         */
        boolean userEhCache = false;
        
        if(userEhCache) {
            return new EhCacheBasedUserCache();
        } else {
            return new SpringCacheBasedUserCache(new ConcurrentMapCache("digestUserCache"));
        }
    }
    
    // @formatter:on
}
