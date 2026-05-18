package com.learn.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class Anagram {

    public static void main(String[] args) {

        String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };

        Anagram anagram = new Anagram();
        List<List<String>> anagrams = anagram.groupAnagrams(strs);

        log.info("Anagrams: {}", () -> anagrams);
    }

    public List<List<String>> groupAnagrams(String[] strs) {

        if (strs == null) {
            return null;
        }

        List<List<String>> results = new ArrayList<>();

        if (strs.length < 2) {
            results.add(List.of(strs));
        } else {
            Map<String, List<String>> map = new HashMap<>();
            for (String str : strs) {
                if (str == null) {
                    continue;
                }
                String key = genkey(str);
                List<String> anagram = map.get(key);
                if (anagram == null) {
                    anagram = new ArrayList<>();
                    map.put(key, anagram);
                }
                anagram.add(str);
            }

            results.addAll(map.values());
        }

        return results;
    }

    public String genkey(String str) {
        List<Character> chs = new ArrayList<>();

        for (int i = 0, n = str.length(); i < n; i++) {
            chs.add(str.charAt(i));
        }

        return chs.stream().sorted().map(e -> String.valueOf(e)).collect(Collectors.joining());
    }
}
