package com.learn.console;


import java.io.Console;


public class ConsoleInput {

    public static void main(String[] args) {

        // create a scanner so we can read the command-line input

        Console console = System.console();
        if (console == null) {
            System.out.println("No console: non-interactive mode!");
            System.exit(0);
            return;
        }

        System.out.print("Enter your username: ");
        String username = console.readLine();
        System.out.println("Your input: " + username);

        System.out.print("Enter your password: ");
        char[] password = console.readPassword();
        System.out.println("Your input: " + new String(password));

        String passport = console.readLine("Enter your %d (th) passport number: ", 2);
        System.out.println("Your input: " + passport);
    }

}
