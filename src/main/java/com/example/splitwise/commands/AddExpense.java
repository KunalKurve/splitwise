package com.example.splitwise.commands;

public class AddExpense implements Command{
    @Override
    public boolean matches(String input) {
        if(input.equalsIgnoreCase("add")){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        System.out.println("Expense has been added");
    }
}
