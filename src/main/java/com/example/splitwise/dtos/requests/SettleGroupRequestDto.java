package com.example.splitwise.dtos.requests;

import com.example.splitwise.models.enums.SettleUpStrategyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettleGroupRequestDto {

    private int groupId;
    private SettleUpStrategyType settleUpStrategyType;
}
