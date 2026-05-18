package com.learn.jsonpath;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.Predicate;
import com.learn.util.ClasspathUtils;

import lombok.extern.log4j.Log4j2;


/**
 * $.widget.window.title             // Dot notation
 * $['widget']['window']['title']    // Bracket notation
 */
@Log4j2
public class JsonPathTest {

    @Test
    public void testRead()
        throws IOException, URISyntaxException {
        log.debug(() -> "Start");

        String json = ClasspathUtils.readString("widget.json");

        String widgetTitle = JsonPath.read(json, "$.widget.window.title");
        Assertions.assertEquals("Client Info", widgetTitle);

        List<String> locations = JsonPath.read(json, "$.widget.window.locations[?(@.display == 'true')].name");
        Assertions.assertTrue(List.of("header", "footer").containsAll(locations));
        log.debug("locations: {}", () -> locations);

        List<String> locations2 = JsonPath.read(json, "$.widget.window.locations[*].name");
        Assertions.assertTrue(List.of("header", "footer", "sidebar").containsAll(locations2));
        log.debug("locations2: {}", () -> locations2);

        List<String> locations3 = JsonPath.read(json, "$.widget.window.locations[0,1].name");
        Assertions.assertTrue(List.of("header", "footer").containsAll(locations3));
        log.debug("locations3: {}", () -> locations3);

        Double largestPadding = JsonPath.read(json, "$.widget.window.padding.max()");
        Assertions.assertEquals(50, largestPadding);

        Predicate displayEnabled = new Predicate() {
            @Override
            public boolean apply(PredicateContext ctx) {
                return ctx.item(Map.class).get("display").toString().equalsIgnoreCase("true");
            }
        };

        List<String> locations4 = JsonPath.read(json, "$.widget.window.locations[?].name", displayEnabled);
        Assertions.assertTrue(List.of("header", "footer").containsAll(locations4));
        log.debug("locations4: {}", () -> locations4);

        /**
         * Use `Configuration`
         */
        Configuration configuration = Configuration
                .builder()
                .options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS)
                .build();

        json = ClasspathUtils.readString("widget.json");

        List<String> widgetTitle2 = JsonPath.using(configuration)
                .parse(json).read("$.widget.window.title");
        log.debug("widgetTitle2: {}", () -> widgetTitle2);

        List<String> locations5 = JsonPath
                .using(configuration)
                .parse(json).read("$.widget.window.locations[0].name");
        Assertions.assertTrue(List.of("header").containsAll(locations5));
        log.debug("locations5: {}", () -> locations5);

        log.debug(() -> "End");
    }
}
