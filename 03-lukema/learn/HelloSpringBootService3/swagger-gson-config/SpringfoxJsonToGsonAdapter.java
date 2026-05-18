package com.hughesntc.co.configuration;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import springfox.documentation.spring.web.json.Json;


public class SpringfoxJsonToGsonAdapter
    implements JsonSerializer<Json> {
    @Override
    public JsonElement serialize(Json json, java.lang.reflect.Type type, JsonSerializationContext context) {
        return JsonParser.parseString(json.value());
    }
}
