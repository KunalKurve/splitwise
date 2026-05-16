package com.example.splitwise.commands;

import com.example.splitwise.controllers.GroupController;
import com.example.splitwise.dtos.requests.CreateGroupRequestDto;
import com.example.splitwise.dtos.responses.CreateGroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateGroup implements Command{

    private final GroupController groupController;
    private final String COMMAND_NAME = "CreateGroup";

    @Autowired
    public CreateGroup(GroupController groupController){
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        String[] commandFragments = input.split(" ");
        if (commandFragments[0].equalsIgnoreCase(COMMAND_NAME)){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        String[] commandFragments = input.split(" ");
        try{
            String groupName = commandFragments[1];
            String description = commandFragments[2];
            int userId = Integer.parseInt(commandFragments[3]);

            CreateGroupRequestDto requestDto = new CreateGroupRequestDto();
            requestDto.setGroupName(groupName);
            requestDto.setDescription(description);
            requestDto.setUserId(userId);

            CreateGroupResponseDto responseDto = groupController.createGroup(requestDto);
            System.out.println(responseDto.getGroupId());
            System.out.println(responseDto.getResponseStatus());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Something went Wrong !");
        }
    }
}
