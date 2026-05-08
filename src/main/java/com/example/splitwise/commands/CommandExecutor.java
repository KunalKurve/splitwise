package com.example.splitwise.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {

    private List<Command> suppportedCommands;

    public CommandExecutor(
//            RegisterUser registerUser,
//            SettleUp settleUp
    ){
        this.suppportedCommands = new ArrayList<>();
//        this.suppportedCommands.add(registerUser);
//        this.suppportedCommands.add(settleUp);
    }

    public void addCommand(Command command){
        this.suppportedCommands.add(command);
    }

    public void removeCommand(Command command){
        this.suppportedCommands.remove(command);
    }

    public void execute(String input){
        for(Command command : suppportedCommands){
            if(command.matches(input)) {
                command.execute(input);
            }
        }
    }
}
