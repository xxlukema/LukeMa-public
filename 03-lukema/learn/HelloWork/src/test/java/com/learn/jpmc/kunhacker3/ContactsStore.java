package com.learn.jpmc.kunhacker3;


import java.util.List;


public interface ContactsStore {

    static String ADD = "ADD";
    static String UPDATE = "UPDATE";
    static String DELETE = "DELETE";

    void command(String command, Contact data);

    List<Contact> find(String partialName);

    List<Contact> all();
}
