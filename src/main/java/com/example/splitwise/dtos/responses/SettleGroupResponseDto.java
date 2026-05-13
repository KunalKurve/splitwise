package com.example.splitwise.dtos.responses;


import java.util.List;

public class SettleGroupResponseDto {

    private List<Transaction> transactions;
    private ResponseStatus responseStatus;
    private String message;
}
