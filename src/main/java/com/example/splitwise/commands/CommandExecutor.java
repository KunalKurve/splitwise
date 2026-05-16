package com.example.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandExecutor {

    private List<Command> supportedCommands;

    @Autowired
    public CommandExecutor(
            RegisterUser registerUser,
            DeleteProfile deleteProfile,
            CreateGroup createGroup,
            DeleteGroup deleteGroup,
            AddUserExpense addUserExpense,
            AddGroupExpense addGroupExpense,
            SettleUpUser settleUpUser,
            SettleUpGroup settleUpGroup,
            FetchAllMembers fetchAllMembers,
            AddMember addMember,
            RemoveMember removeMember
    ){
        this.supportedCommands = new ArrayList<>();
        this.supportedCommands.add(registerUser);
        supportedCommands.add(deleteProfile);
        supportedCommands.add(settleUpGroup);
        supportedCommands.add(createGroup);
        supportedCommands.add(deleteGroup);
        supportedCommands.add(addUserExpense);
        supportedCommands.add(addGroupExpense);
        supportedCommands.add(settleUpUser);
        supportedCommands.add(settleUpGroup);
        supportedCommands.add(fetchAllMembers);
        supportedCommands.add(addMember);
        supportedCommands.add(removeMember);
    }

    public void addCommand(Command command){
        this.supportedCommands.add(command);
    }

    public void removeCommand(Command command){
        this.supportedCommands.remove(command);
    }

    public void execute(String input){
        for(Command command : supportedCommands){
            if(command.matches(input)) {
                command.execute(input);
            }
        }
    }
}
