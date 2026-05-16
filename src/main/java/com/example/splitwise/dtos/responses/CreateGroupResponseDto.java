package com.example.splitwise.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateGroupResponseDto {

    private ResponseStatus responseStatus;
    private int groupId;
}
