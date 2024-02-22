package com.griddynamics.yvoronovskyi.battleship;

import java.util.Scanner;
public class ConsoleReader {
    private final static Scanner scanner = new Scanner(System.in);
    public static String getString() {
        return scanner.nextLine();
    }
}
