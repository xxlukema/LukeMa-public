package com.learn.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.learn.interceptor.UserInterceptor;


@Configuration
/**
 * Must remove @EnableWebMvc to make swagger-ui.html work!!
 */
// @EnableWebMvc
public class SpringMvcViewResolverConfig
    implements WebMvcConfigurer {

    @Bean
    UserInterceptor getUserInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUserInterceptor()).addPathPatterns("/spring/user/**");
    }

    /**
     * Needed to load css and js files.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * This is also good:
         * 
         * registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
         */
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/formvalidation").setViewName("formvalidation");
        registry.addViewController("/datatable").setViewName("datatable");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/jwtlogin").setViewName("jwtlogin");
        registry.addViewController("/logout").setViewName("logout");
        registry.addViewController("/400").setViewName("error/400");
        registry.addViewController("/403").setViewName("error/403");
        // 404 is mapped inside SpringMvcController.java
        registry.addViewController("/405").setViewName("error/405");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/greeting").setViewName("greeting");
        registry.addViewController("/AngularJS/directive").setViewName("AngularJS/directive");
        registry.addViewController("/AngularJS/filter").setViewName("AngularJS/filter");
    }

}
