package com.example.splitwise.strategies;

import com.example.splitwise.models.enums.SplitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SplitStrategyFactory {

    private final EqualSplitStrategy equalSplitStrategy;
    private final PercentageSplitStrategy percentageSplitStrategy;
    private final ExactSplitStrategy exactSplitStrategy;

    @Autowired
    public SplitStrategyFactory(
            EqualSplitStrategy equalSplitStrategy,
            PercentageSplitStrategy percentageSplitStrategy,
            ExactSplitStrategy exactSplitStrategy
    ) {
        this.equalSplitStrategy = equalSplitStrategy;
        this.percentageSplitStrategy = percentageSplitStrategy;
        this.exactSplitStrategy = exactSplitStrategy;
    }

    public SplitStrategy getSplitStrategy(SplitType splitType){

        return switch (splitType) {

            case EQUAL -> equalSplitStrategy;

            case PERCENTAGE -> percentageSplitStrategy;

            case EXACT -> exactSplitStrategy;
        };
    }

}
