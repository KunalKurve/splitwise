package com.example.splitwise.commands;

public class DeleteProfile implements Command{
    @Override
    public boolean matches(String input) {
        if(input.equalsIgnoreCase("delete")){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        System.out.println("Profile has been deleted");
    }
}
