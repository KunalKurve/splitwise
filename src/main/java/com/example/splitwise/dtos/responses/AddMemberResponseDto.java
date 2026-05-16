package com.example.splitwise.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberResponseDto {

    private int groupMemberId;
    private ResponseStatus responseStatus;
}
