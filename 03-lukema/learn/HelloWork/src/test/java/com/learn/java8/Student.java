package com.learn.java8;


import java.util.HashSet;
import java.util.Set;


public class Student {

    private String name;
    private Set<String> bookNameSet;

    public void addToBookNameSet(String bookName) {
        if (this.bookNameSet == null) {
            this.bookNameSet = new HashSet<>();
        }
        this.bookNameSet.add(bookName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getBookNameSet() {
        return bookNameSet;
    }

    public void setBookNameSet(Set<String> bookNameSet) {
        this.bookNameSet = bookNameSet;
    }

}
