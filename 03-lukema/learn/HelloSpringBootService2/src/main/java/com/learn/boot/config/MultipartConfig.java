package com.learn.boot.config;


/**
 * Placeholder for multipartResolver.
 * With "new CommonsMultipartResolver()", multipart file attachement will not work.
 * Therefore, that bean creation must be commented out, or @Configuration must be commented out. 
 */
// @Configuration
public class MultipartConfig {

    /**
     * Placeholder for multipartResolver.
     * With "new CommonsMultipartResolver()", multipart file attachement will not work.
     * Therefore, that bean creation must be commented out, or @Configuration must be commented out.
     */
    /*
    @Bean
    @Conditional(OnLinuxOrUnixCondition.class)
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        return multipartResolver;
    }
    */

}
