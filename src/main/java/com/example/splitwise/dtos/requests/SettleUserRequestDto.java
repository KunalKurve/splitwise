package com.example.splitwise.dtos.requests;

import com.example.splitwise.models.enums.SettleUpStrategyType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SettleUserRequestDto {

    private int userId;
    private SettleUpStrategyType settleUpStrategyType;
}
