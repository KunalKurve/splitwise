package com.example.splitwise.commands;

import com.example.splitwise.controllers.SettleUpController;
import com.example.splitwise.dtos.requests.SettleUserRequestDto;
import com.example.splitwise.dtos.responses.SettleUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpUser implements Command{

    private final SettleUpController settleUpController;
    private final String COMMAND_NAME = "SettleUpUser";

    @Autowired
    public SettleUpUser(SettleUpController settleUpController){
        this.settleUpController = settleUpController;
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
        //settle up
        String[] commandFragments = input.split(" ");
        try {
            int id = Integer.parseInt(commandFragments[1]);
            SettleUserRequestDto requestDto = new SettleUserRequestDto();
            requestDto.setUserId(id);
            SettleUserResponseDto responseDto = settleUpController.settleUpUser(requestDto);
            System.out.println(responseDto.getMessage());
            System.out.println(responseDto.getTransactions());
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pass a Valid User Id");
        }
    }
}
