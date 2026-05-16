package com.example.splitwise.commands;

import com.example.splitwise.controllers.UserController;
import com.example.splitwise.dtos.requests.RegisterUserRequestDto;
import com.example.splitwise.dtos.responses.RegisterUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUser implements Command{

    private final UserController userController;
    private final String COMMAND_NAME = "RegisterUser";

    @Autowired
    public RegisterUser(UserController userController){
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        String[] commandFragments = input.split(" ");
        if(commandFragments[0].equalsIgnoreCase(COMMAND_NAME)){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        String[] commandFragments = input.split(" ");
        try{
            String username = commandFragments[1];
            String password = commandFragments[2];
            String phone = commandFragments[3];
            RegisterUserRequestDto requestDto = new RegisterUserRequestDto();
            requestDto.setUsername(username);
            requestDto.setPassword(password);
            requestDto.setPhone(phone);

            RegisterUserResponseDto responseDto = userController.registerUser(requestDto);
            System.out.println(responseDto.getUserId());
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Something went Wrong !");
        }
    }
}
