package com.learn.util;


import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class PropertyLogger {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("================== Environment and configuration ==================");
        /*
        sb.append(System.lineSeparator());
        final Environment env = event.getApplicationContext().getEnvironment();
        sb.append("Active profiles: ").append(Arrays.toString(env.getActiveProfiles())).append(System.lineSeparator());
        final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
        // @formatter:off
        StreamSupport.stream(sources.spliterator(), false)
            .filter(ps -> ps instanceof EnumerablePropertySource)
            .map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
            .flatMap(Arrays::stream)
            .distinct()
            .filter(prop -> !(prop.contains("credentials") || prop.contains("password") || prop.contains("java.class.path")
                              || prop.contains("java.library.path")
                              || prop.contains("ORIGINAL_PATH") || prop.contains("INFOPATH") || prop.contains("PATH")
                              || prop.contains("IGCCSVC_DB")))
            .forEach(prop -> sb.append(prop).append(": ").append(env.getProperty(prop)).append(System.lineSeparator()));
        // @Formatter:on
        // sb.delete(sb.lastIndexOf(System.lineSeparator()), sb.length());
        sb.append("================== End Environment and configuration ==================");
        */
        log.debug(sb.toString());
    }

}
