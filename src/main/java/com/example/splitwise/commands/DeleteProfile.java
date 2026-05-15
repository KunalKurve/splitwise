package com.example.splitwise.commands;

import com.example.splitwise.controllers.UserController;
import com.example.splitwise.dtos.requests.DeleteProfileRequestDto;
import com.example.splitwise.dtos.requests.RegisterUserRequestDto;
import com.example.splitwise.dtos.responses.DeleteProfileResponseDto;
import com.example.splitwise.dtos.responses.RegisterUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteProfile implements Command{

    private final UserController userController;
    private final String COMMAND_NAME = "DeleteProfile";

    @Autowired
    public DeleteProfile(UserController userController){
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
            int userId = Integer.parseInt(commandFragments[1]);

            DeleteProfileRequestDto requestDto = new DeleteProfileRequestDto();
            requestDto.setUserId(userId);

            DeleteProfileResponseDto responseDto = new DeleteProfileResponseDto();
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Something went Wrong !");
        }
    }
}
