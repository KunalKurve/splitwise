package com.example.splitwise.utils;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.ExpenseUser;
import com.example.splitwise.models.User;
import com.example.splitwise.models.enums.ExpenseUserType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseUtils {

    public static Map<User, Double> aggregateExpenses(List<Expense> expenses){

        Map<User, Double> userExpenses = new HashMap<>();

        for(Expense expense : expenses) {
            for(ExpenseUser expenseUser : expense.getUserExpenses()){

                Double userAmount = expenseUser.getExpenseUserType() == ExpenseUserType.PAID ?
                        expenseUser.getAmount() : -1 * expenseUser.getAmount();

                userExpenses.put(expenseUser.getUser(),
                        userExpenses.getOrDefault(
                                expenseUser.getUser(), 0d) + userAmount);
            }
        }

        return userExpenses;
    }
}
