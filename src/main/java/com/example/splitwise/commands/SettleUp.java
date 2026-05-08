package com.example.splitwise.commands;

public class SettleUp implements Command{

    @Override
    public boolean matches(String input) {
        if(input.equalsIgnoreCase("settle")){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        //settle up
        System.out.println("Expense has been settled up");
    }
}
