package com.example.splitwise.commands;

import com.example.splitwise.controllers.SettleUpController;
import com.example.splitwise.dtos.requests.SettleGroupRequestDto;
import com.example.splitwise.dtos.responses.SettleGroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpGroup implements Command{

    private final SettleUpController settleUpController;
    private final String COMMAND_NAME = "SettleUpGroup";

    @Autowired
    public SettleUpGroup(SettleUpController settleUpController){
        this.settleUpController = settleUpController;
    }

    @Override
    public boolean matches(String input) {
        String[] commandFragments = input.split(" ");
        if (commandFragments[0].equalsIgnoreCase(COMMAND_NAME)) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        String[] commandFragments = input.split(" ");
        try {
            int id = Integer.parseInt(commandFragments[1]);
            SettleGroupRequestDto requestDto = new SettleGroupRequestDto();
            requestDto.setGroupId(id);

            SettleGroupResponseDto responseDto = settleUpController.settleUpGroup(requestDto);
            System.out.println(responseDto.getMessage());
            System.out.println(responseDto.getTransactions());
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pass a valid GroupId");
        }
    }
}
