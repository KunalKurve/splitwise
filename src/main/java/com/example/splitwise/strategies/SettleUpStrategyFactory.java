package com.example.splitwise.strategies;

public class SettleUpStrategyFactory {

    public static SettleUpStrategy getSettleUpStrategy(String strategyName){

        if(strategyName.equalsIgnoreCase("PlusMinus")){
            return new PlusMinusSettleUpStrategy();
        }
        else if(strategyName.equalsIgnoreCase("TwoSets")){
            return new TwoSetsSettleUpStrategy();
        }
        return null;
    }
}
