package com.example.splitwise.controllers;

import com.example.splitwise.dtos.requests.*;
import com.example.splitwise.dtos.responses.*;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupMember;
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

    public AddMemberResponseDto addMember(AddMemberRequestDto requestDto){
        AddMemberResponseDto responseDto = new AddMemberResponseDto();
        try{
            GroupMember groupMember = groupService.addMember(
                    requestDto.getGroupId(),
                    requestDto.getAdminId(),
                    requestDto.getMemberToBeAddedUserId()
            );
            responseDto.setGroupMemberId(groupMember.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }

    public RemoveMemberResponseDto removeMember(RemoveMemberRequestDto requestDto){
        RemoveMemberResponseDto responseDto = new RemoveMemberResponseDto();
        try{
            groupService.removeMember(
                    requestDto.getGroupId(),
                    requestDto.getAdminId(),
                    requestDto.getMemberId()
            );
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }
}
