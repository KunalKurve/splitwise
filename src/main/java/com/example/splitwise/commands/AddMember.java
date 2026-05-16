package com.example.splitwise.commands;

import com.example.splitwise.controllers.GroupController;
import com.example.splitwise.dtos.requests.AddMemberRequestDto;
import com.example.splitwise.dtos.requests.FetchAllMembersRequestDto;
import com.example.splitwise.dtos.responses.AddMemberResponseDto;
import com.example.splitwise.dtos.responses.FetchAllMembersResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddMember implements Command{
    private final GroupController groupController;
    private final String COMMAND_NAME = "AddMember";

    @Autowired
    public AddMember(GroupController groupController){
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
            int adminId = Integer.parseInt(commandFragments[2]);
            int userId = Integer.parseInt(commandFragments[3]);

            AddMemberRequestDto requestDto = new AddMemberRequestDto();
            requestDto.setGroupId(groupId);
            requestDto.setAdminId(adminId);
            requestDto.setMemberToBeAddedUserId(userId);

            AddMemberResponseDto responseDto = groupController.addMember(requestDto);
            System.out.println(responseDto.getGroupMemberId());
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Something went Wrong !");
        }
    }
}
