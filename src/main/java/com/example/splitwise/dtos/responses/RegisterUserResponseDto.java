package com.example.splitwise.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponseDto {

    private int userId;
    private ResponseStatus responseStatus;
}
