package com.example.splitwise.commands;

import com.example.splitwise.controllers.GroupController;
import com.example.splitwise.dtos.requests.DeleteProfileRequestDto;
import com.example.splitwise.dtos.requests.FetchAllMembersRequestDto;
import com.example.splitwise.dtos.responses.DeleteProfileResponseDto;
import com.example.splitwise.dtos.responses.FetchAllMembersResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchAllMembers implements Command{

    private final GroupController groupController;
    private final String COMMAND_NAME = "FetchAllMembers";

    @Autowired
    public FetchAllMembers(GroupController groupController){
        this.groupController = groupController;
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
            int groupId = Integer.parseInt(commandFragments[1]);
            int userId = Integer.parseInt(commandFragments[2]);

            FetchAllMembersRequestDto requestDto = new FetchAllMembersRequestDto();
            requestDto.setGroupId(groupId);
            requestDto.setUserId(userId);

            FetchAllMembersResponseDto responseDto = groupController.fetchAllMembers(requestDto);
            System.out.println(responseDto.getMembers());
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Something went Wrong !");
        }
    }
}
