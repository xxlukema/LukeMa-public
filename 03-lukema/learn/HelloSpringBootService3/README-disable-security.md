# Disable security

Three options

## Option 1: `@SpringBootApplication(..., exclude = {...})`

    @SpringBootApplication(scanBasePackages = { "com.learn" },
        exclude = {
            org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
            org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
        }
    )

## Option 2: `application.yml`

    spring:
      autoconfigure:
        exclude:
          - org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,
          - org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration

    user:
      name: user    <=== (default. optional.)
      password: place-holder-not-in-use

## Option 3. `@Profile("no-security")`

    @Configuration
    @Profile("no-security")
    public class MySecurityConfig {
    
        @Bean
        SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.cors(Customizer.withDefaults())
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests((authorize) -> {
                        authorize.requestMatchers("/**").permitAll();
                        // ...
                        authorize.anyRequest().authenticated();
                    })
                    .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                    .httpBasic(withDefaults());
    
            return httpSecurity.build();
        }
    }

### 3.1 Specify `"no-security"` in `application.yml`

    spring:
      profiles:
        include:
          - no-security
          - dev

### 3.2 Or, cmd with maven

    mvn spring-boot:run -Dspring-boot.run.profiles=no-security,dev

### 3.3 Or, in Dockerfile

    java -jar filename-1.0.0.war -Dspring.profiles.active=no-security,dev
