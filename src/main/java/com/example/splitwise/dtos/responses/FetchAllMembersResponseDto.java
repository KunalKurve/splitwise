package com.example.splitwise.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FetchAllMembersResponseDto {

    private ResponseStatus responseStatus;
    private List<MemberDto> members;
}