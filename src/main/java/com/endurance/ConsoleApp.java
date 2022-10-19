package com.endurance;

import picocli.CommandLine;

public class ConsoleApp {

    public static void main(String[] args) {
        new CommandLine(new ConsoleApp()).execute(args);
    }
}
