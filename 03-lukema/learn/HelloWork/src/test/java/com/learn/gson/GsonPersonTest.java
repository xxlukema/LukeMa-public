package com.learn.gson;


import java.lang.reflect.Type;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class GsonPersonTest {

    @Test
    public void testJsonArrayToObjectList() {
        log.debug(() -> "Start");

        Gson gson = new Gson();

        String json = """
                {
                   "company":"hello world",
                   "data": [
                     {"age": 10, "name": "AAA"},
                     {"age": 20, "name": "BBB"},
                     {"age": 30, "name": "CCC"}
                   ]
                 }
                """;

        // Converts JSON to JsonElement
        JsonElement element = gson.fromJson(json, JsonElement.class);

        if (element.isJsonObject()) {

            JsonObject obj = element.getAsJsonObject();

            // Get the `data` array
            JsonArray data = obj.getAsJsonArray("data");

            // creates a List<GsonPerson> type
            Type personListType = new TypeToken<List<GsonPerson>>() {
            }.getType();

            // converts the `data` array to List<Person>
            List<GsonPerson> list = gson.fromJson(data, personListType);

            for (GsonPerson gsonPerson : list) {
                log.debug(() -> gsonPerson);
            }

        }

        log.debug(() -> "End");
    }
}
