package com.example.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandExecutor {

    private List<Command> suppportedCommands;

    @Autowired
    public CommandExecutor(
            RegisterUser registerUser,
            DeleteProfile deleteProfile,
            CreateGroup createGroup,
            DeleteGroup deleteGroup,
            AddUserExpense addUserExpense,
            AddGroupExpense addGroupExpense,
            SettleUpUser settleUpUser,
            SettleUpGroup settleUpGroup
    ){
        this.suppportedCommands = new ArrayList<>();
        this.suppportedCommands.add(registerUser);
        suppportedCommands.add(deleteProfile);
        suppportedCommands.add(settleUpGroup);
        suppportedCommands.add(createGroup);
        suppportedCommands.add(deleteGroup);
        suppportedCommands.add(addUserExpense);
        suppportedCommands.add(addGroupExpense);
        suppportedCommands.add(settleUpUser);
        suppportedCommands.add(settleUpGroup);
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
