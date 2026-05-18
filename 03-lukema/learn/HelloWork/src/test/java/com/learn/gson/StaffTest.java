package com.learn.gson;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.learn.util.ClasspathUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StaffTest {

    @Test
    public void testObjectToJson()
        throws IOException {
        log.debug(() -> "Start");

        // default
        //Gson gson = new Gson();

        // enable pretty print
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // create a staff object for testing
        Staff staff = new Staff();

        staff.setName("mkyong");
        staff.setAge(42);
        staff.setPosition(new String[] { "Founder", "CTO", "Writer" });

        Map<String, BigDecimal> salary = new HashMap<>();
        salary.put("2010", new BigDecimal(10000));
        salary.put("2012", new BigDecimal(12000));
        salary.put("2018", new BigDecimal(14000));
        staff.setSalary(salary);

        staff.setSalary(salary);
        staff.setSkills(Arrays.asList("java", "python", "node", "kotlin"));
        staff.setActive(true);

        // Converts Java object to String
        String json = gson.toJson(staff);
        System.out.println(json);

        // Converts Java object to File
        try (Writer writer = new FileWriter("target/staff.json")) {
            gson.toJson(staff, writer);
        }

        log.debug(() -> "End");
    }

    @Test
    public void testJsonToObject()
        throws IOException {
        log.debug(() -> "Start");

        // default compact print
        Gson gson = new Gson();

        try (Reader reader = new FileReader(ClasspathUtils.getPasthAsString("staff.json"))) {

            // Convert JSON File to Java Object
            Staff staff = gson.fromJson(reader, Staff.class);

            // print staff object
            log.debug("staff: {}", () -> staff);
        }

        log.debug(() -> "End");
    }

    @Test
    public void testJsonToObject2()
        throws IOException, URISyntaxException {
        log.debug(() -> "Start");

        // default compact print
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = ClasspathUtils.readString("staff.json");
        log.debug("json: {}", () -> json);

        // Convert JSON File to Java Object
        Staff staff = gson.fromJson(json, Staff.class);

        // print staff object
        log.debug("staff: {}", () -> staff);

        log.debug(() -> "End");
    }

    @Test
    public void testModifyJsonWithJsonElement()
        throws IOException, URISyntaxException {
        log.debug(() -> "Start");

        String json = ClasspathUtils.readString("staff.json");

        Gson gson = new Gson();

        // Converts JSON to JsonElement
        JsonElement element = gson.fromJson(json, JsonElement.class);

        // modify JSON data
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            // remove position and salary
            obj.remove("position");
            obj.remove("salary");

            // add or update age
            obj.addProperty("age", 99);

            // add a JsonArray
            JsonArray jsonArray = new JsonArray();
            jsonArray.add("spring boot");
            jsonArray.add("javascript");
            obj.add("skills", jsonArray);
        }

        // Converts JsonElement to Java object
        Staff staff = gson.fromJson(element, Staff.class);
        log.debug("staff: {}", () -> staff);

        log.debug(() -> "End");
    }

}
