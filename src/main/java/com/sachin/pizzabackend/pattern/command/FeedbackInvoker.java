package com.sachin.pizzabackend.pattern.command;

public class FeedbackInvoker {
    private Command Command;

    // Set the command
    public void setFeedbackCommand(Command Command) {
        this.Command = Command;
    }

    // Execute the command
    public void executeCommand() {
        Command.execute();
    }
}
