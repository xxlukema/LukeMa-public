package com.learn.boot.config;


import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;


/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class BootSecurityConfig
    extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * For JDBC authentication
     * 
     * import javax.sql.DataSource;
     * */
    @Autowired
    private DataSource dataSource;

    // @formatter:off
    
    @Override
    public void configure(WebSecurity web)
        throws Exception {

        LOG.debug("Called.");
        
        /**
         *  Spring Security should completely ignore URLs starting with /resources/
         *  
         *  This maps all directories under src/main/resources/static
         */
        web.ignoring().antMatchers("/css/**")
                      .antMatchers("/js/**")
                      .antMatchers("/image/**");
    }

    /**
     * roles admin allow to access /admin/**
     * roles user allow to access /user/**
     * custom 403 access denied handler
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        LOG.debug("Called.");
        
        /**
         * CSFR is enbaled ob spring by default.
         * 
         * http.csrf().disable();
         */
        httpSecurity.authorizeRequests()
                        .antMatchers("/", "/home", "/about", "/error").permitAll()
                        .antMatchers("/AngularJS/**").permitAll()
                        .antMatchers("/spring/**", "/rest/**").permitAll()
                        .antMatchers("/admin/**").hasAnyRole("ADMIN")
                        .antMatchers("/user/**").hasAnyRole("USER")
                        .anyRequest().authenticated()
                        .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                        .and()
                    .logout()
                        .permitAll()
                        .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        
        /**
         * Mandatory!! Without this line, security will mess up.
         */
        httpSecurity.csrf().disable();
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
     */
    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        LOG.debug("Called.");
        
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("USER")
                .and()
                .withUser("admin").password("{noop}admin").roles("ADMIN")
                .and()
                .withUser("xma").password("{noop}xma").roles("USER", "ADMIN");
    }
    */

    /**
     * For JDBC Authentication
     * 
     * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
     * import org.springframework.security.crypto.password.PasswordEncoder;
     * */ 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT u.username, ur.authority FROM users u, user_roles ur WHERE u.user_id = ur.user_id AND u.username = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @formatter:on

    @Bean
    public PasswordEncoder passwordEncoder1() {

        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                LOG.debug("Called. " + rawPassword + " " + encodedPassword);

                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder2() {

        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                LOG.debug("Called. " + rawPassword + " " + encodedPassword);

                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }

    /*
    @Bean
    public SpringTemplateEngine templateEngine() {
        Set<IDialect> set = new HashSet<IDialect>();
        set.add(new SpringSecurityDialect());
    
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setAdditionalDialects(set);
    
        return springTemplateEngine;
    }
    */
}
