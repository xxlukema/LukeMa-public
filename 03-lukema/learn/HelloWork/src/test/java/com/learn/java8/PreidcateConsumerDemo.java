package com.learn.java8;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PreidcateConsumerDemo {

    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {

        StudentWithFee student1 = new StudentWithFee("Ashok", "Kumar", 9.5);
        student1 = updateStudentFee(student1,
                //Lambda expression for Predicate interface
                student -> student.grade > 8.5,
                //Lambda expression for Consumer interface
                student -> student.feeDiscount = 30.0);
        student1.printFee();

        StudentWithFee student2 = new StudentWithFee("Rajat", "Verma", 8.0);
        student2 = updateStudentFee(student2, student -> student.grade >= 8, student -> student.feeDiscount = 20.0);
        student2.printFee();

        List<StudentWithFee> list = Arrays.asList(student1, student2);

        list.forEach(LOG::info);

        list.forEach(item -> {
            item.printFee();
        });

        list.forEach(item -> item.printFee());

        Consumer<String> consumer = PreidcateConsumerDemo::printNames;

        consumer.accept("Jeremy");
        consumer.accept("Paul");
        consumer.accept("Richard");

        List<String> names = new ArrayList<>();
        names.add("David");
        names.add("Sam");
        names.add("Ben");

        names.stream().forEach((x) -> {
            printSupplerNames(() -> x);
        });
    }

    private static void printNames(String name) {
        System.out.println(name);
    }

    static void printSupplerNames(Supplier<?> arg) {
        System.out.println(arg.get());
    }

    public static StudentWithFee updateStudentFee(StudentWithFee student, Predicate<StudentWithFee> predicate, Consumer<StudentWithFee> consumer) {

        //Use the predicate to decide when to update the discount.
        if (predicate.test(student)) {
            //Use the consumer to update the discount value.
            consumer.accept(student);
        }
        return student;
    }

}
