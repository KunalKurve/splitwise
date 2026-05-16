package com.example.splitwise.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SplitRequestDto {

    private Integer userId;

    private Double amount;

    private Double percentage;
}
