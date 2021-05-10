package data.netdata;

import java.io.Serializable;

public class Request implements Serializable {
    private String loginClient;
    private String passwordClient;

    private String commandName;
    private String argument;
    private Serializable objectArgument;

    public Request(String commandName, String argument, Serializable objectArgument) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = objectArgument;
    }

    public Request(String commandName, String argument) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = null;
    }

    public Request() {
        this.commandName = "";
        this.argument = "";
        this.objectArgument = null;
    }

    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }

    public void setPasswordClient(String passwordClient) {
        this.passwordClient = passwordClient;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    public Object getObjectArgument() {
        return objectArgument;
    }

    public String getLoginClient() {
        return loginClient;
    }

    public String getPasswordClient() {
        return passwordClient;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && argument.isEmpty() && objectArgument == null;
    }

    @Override
    public String toString() {
        return "Request{" +
                "loginClient='" + loginClient + '\'' +
                ", commandName='" + commandName + '\'' +
                ", argument='" + argument + '\'' +
                ", objectArgument=" + objectArgument +
                '}';
    }
}
