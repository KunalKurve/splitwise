package com.example.splitwise.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaidByDto {

    private Integer userId;

    private Double amount;
}
