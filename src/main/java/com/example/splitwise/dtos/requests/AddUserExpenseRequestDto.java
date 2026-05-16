package com.example.splitwise.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserExpenseRequestDto {

    private int userId;
    private double amount;
}
