package com.example.splitwise.strategies;

import com.example.splitwise.models.enums.SettleUpStrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettleUpStrategyFactory {

    private final PlusMinusSettleUpStrategy plusMinusSettleUpStrategy;
    private final TwoSetsSettleUpStrategy twoSetsSettleUpStrategy;

    @Autowired
    public SettleUpStrategyFactory(
            PlusMinusSettleUpStrategy plusMinusSettleUpStrategy,
            TwoSetsSettleUpStrategy twoSetsSettleUpStrategy
    ){
        this.plusMinusSettleUpStrategy = plusMinusSettleUpStrategy;
        this.twoSetsSettleUpStrategy = twoSetsSettleUpStrategy;
    }


    public SettleUpStrategy getSettleUpStrategy(SettleUpStrategyType settleUpStrategyType){

        return switch (settleUpStrategyType) {

            case PLUS_MINUS -> plusMinusSettleUpStrategy;

            case TWO_SETS -> twoSetsSettleUpStrategy;
        };
    }
}
