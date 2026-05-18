package com.learn.jpmc.kunhacker3;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ContactsStoreImpl
    implements ContactsStore {

    private Map<Long, Contact> map = new HashMap<>();

    @Override
    public void command(String command, Contact data) {

        switch (command) {
            case ADD:
                map.put(data.getNationalId(), data);
                break;
            case UPDATE:
                if (map.keySet().contains(data.getNationalId())) {
                    map.put(data.getNationalId(), data);
                }
                break;
            case DELETE:
                map.remove(data.getNationalId());
                break;
            default:
                break;
        }

    }

    @Override
    public List<Contact> find(String partialName) {

        // @formatter:off
        return map.entrySet().parallelStream()
                .filter(item -> item.getValue().getName() != null)
                .filter(item -> item.getValue().getName().contains(partialName))
                .map(item -> item.getValue())
                .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public List<Contact> all() {
     // @formatter:off
        return map.entrySet().parallelStream()
                .filter(item -> item.getValue().getName() != null)
                .map(item -> item.getValue())
                .collect(Collectors.toList());
        // @formatter:on
    }

}
