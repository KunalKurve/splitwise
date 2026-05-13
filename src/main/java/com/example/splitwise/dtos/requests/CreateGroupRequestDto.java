package com.example.splitwise.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupRequestDto {

    private int userId;
    private String groupName;
    private String description;
}
