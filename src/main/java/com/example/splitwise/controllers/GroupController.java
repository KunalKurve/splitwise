package com.example.splitwise.controllers;

import com.example.splitwise.dtos.requests.CreateGroupRequestDto;
import com.example.splitwise.dtos.requests.DeleteGroupRequestDto;
import com.example.splitwise.dtos.requests.SettleGroupRequestDto;
import com.example.splitwise.dtos.requests.SettleUserRequestDto;
import com.example.splitwise.dtos.responses.*;
import com.example.splitwise.models.Group;
import com.example.splitwise.services.GroupService;
import com.example.splitwise.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto requestDto){
        CreateGroupResponseDto responseDto = new CreateGroupResponseDto();
        try{
            Group group = groupService.createGroup(
                    requestDto.getGroupName(),
                    requestDto.getDescription(),
                    requestDto.getUserId()
            );
            responseDto.setGroupId(group.getId());
            responseDto.setAdminId(group.getAdmin().getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }

    public DeleteGroupResponseDto deleteGroup(DeleteGroupRequestDto requestDto){
        DeleteGroupResponseDto responseDto = new DeleteGroupResponseDto();
        try{
            groupService.deleteGroup(
                    requestDto.getGroupId(),
                    requestDto.getAdminId()
            );
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }
}
