package com.example.splitwise.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserExpenseResponseDto {

    private ResponseStatus responseStatus;
    private int expenseId;

}
