package com.example.splitwise.commands;

import com.example.splitwise.controllers.GroupController;
import com.example.splitwise.dtos.requests.FetchAllMembersRequestDto;
import com.example.splitwise.dtos.requests.RemoveMemberRequestDto;
import com.example.splitwise.dtos.responses.FetchAllMembersResponseDto;
import com.example.splitwise.dtos.responses.RemoveMemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveMember implements Command{

    private final GroupController groupController;
    private final String COMMAND_NAME = "RemoveMember";

    @Autowired
    public RemoveMember(GroupController groupController){
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
            int memberId = Integer.parseInt(commandFragments[3]);

            RemoveMemberRequestDto requestDto = new RemoveMemberRequestDto();
            requestDto.setGroupId(groupId);
            requestDto.setGroupId(groupId);
            requestDto.setMemberId(memberId);

            RemoveMemberResponseDto responseDto = groupController.removeMember(requestDto);
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Something went Wrong !");
        }
    }
}
