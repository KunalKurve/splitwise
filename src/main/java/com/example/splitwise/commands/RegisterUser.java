package com.example.splitwise.commands;

public class RegisterUser implements  Command{
    @Override
    public boolean matches(String input) {
        if(input.equalsIgnoreCase("register")){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        //register user
        System.out.println("User has been registered");
    }
}
