package com.learn.boot.config;


import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@EnableWebMvc
//@Configuration
//@ComponentScan(basePackages = { "com.learn" })
public class DefaultView
    implements WebMvcConfigurer {

    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/WelcomePage.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
    */
}
