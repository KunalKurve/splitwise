package com.example.splitwise.controllers;

import com.example.splitwise.dtos.requests.DeleteProfileRequestDto;
import com.example.splitwise.dtos.requests.RegisterUserRequestDto;
import com.example.splitwise.dtos.responses.DeleteProfileResponseDto;
import com.example.splitwise.dtos.responses.RegisterUserResponseDto;
import com.example.splitwise.dtos.responses.ResponseStatus;
import com.example.splitwise.models.User;
import com.example.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto){
        RegisterUserResponseDto responseDto = new RegisterUserResponseDto();
        try{
            User user = userService.createUser(requestDto.getUsername(),
                    requestDto.getPassword(),
                    requestDto.getPhone()
            );
            responseDto.setUserId(user.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }

    public DeleteProfileResponseDto deleteProfile(DeleteProfileRequestDto requestDto){
        DeleteProfileResponseDto responseDto = new DeleteProfileResponseDto();
        try{
            userService.deleteUser(requestDto.getUserId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }
}
