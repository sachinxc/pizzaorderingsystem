package com.sachin.pizzabackend.pattern.command;

public class UserCommandInvoker {
    public void executeCommand(Command command) {
        command.execute();
    }
}
