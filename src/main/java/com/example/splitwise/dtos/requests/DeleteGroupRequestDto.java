package com.example.splitwise.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteGroupRequestDto {

    private int groupId;
    private int adminId;

}
