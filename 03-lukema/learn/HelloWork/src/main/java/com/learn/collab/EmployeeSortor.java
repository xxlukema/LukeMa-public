package com.learn.collab;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class EmployeeSortor {

    public static void sortByName(List<Employee> list) {

        Comparator<Employee> comparator = new Comparator<Employee>() {

            @Override
            public int compare(Employee o1, Employee o2) {

                // TODO: handles null pointers

                return o1.getName().compareTo(o2.getName());
            }
        };

        Collections.sort(list, comparator);
    }

    public static void sortByAge(List<Employee> list) {

        Collections.sort(list, (o1, o2) -> o1.getAge() - o2.getAge());
    }

}
