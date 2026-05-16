package com.example.splitwise.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

    private int id;
    private String name;
    private String phone;
}