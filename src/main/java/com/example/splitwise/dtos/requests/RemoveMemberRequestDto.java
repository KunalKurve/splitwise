package com.example.splitwise.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveMemberRequestDto {

    private int groupId;
    private int adminId;
    private int memberId;
}
