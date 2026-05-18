package com.learn.cucumber;


import java.util.HashMap;
import java.util.Map;

import lombok.Data;


@Data
public class HelloCucumber {

    private Map<String, Situation> lookupMap;

    public HelloCucumber() {
        this.lookupMap = new HashMap<>();
        this.lookupMap.put("name1", new Situation(5, "success"));
        this.lookupMap.put("name2", new Situation(7, "Fail"));
    }

    public Situation getSituationByName(String name) {
        return this.lookupMap.get(name);
    }

}
