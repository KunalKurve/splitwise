package com.example.splitwise.dtos.responses;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleGroupResponseDto {

    private List<Transaction> transactions;
    private ResponseStatus responseStatus;
    private String message;
}
