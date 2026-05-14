package com.example.splitwise.strategies;

import com.example.splitwise.dtos.responses.Transaction;
import com.example.splitwise.models.Expense;

import java.util.List;

public class PlusMinusSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        return List.of();
    }
}
