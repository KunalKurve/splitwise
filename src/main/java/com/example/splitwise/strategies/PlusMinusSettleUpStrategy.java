package com.example.splitwise.strategies;

import com.example.splitwise.dtos.responses.Transaction;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.ExpenseUser;
import com.example.splitwise.models.User;
import com.example.splitwise.models.enums.ExpenseUserType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlusMinusSettleUpStrategy
        implements SettleUpStrategy {

    @Override
    public List<Transaction> settleUp(
            List<Expense> expenses
    ) {

        Map<User, Double> balanceMap = new HashMap<>();

        /*
         Calculate net balance
         +ve => receive money
         -ve => pay money
        */

        for (Expense expense : expenses) {

            for (ExpenseUser expenseUser : expense.getUserExpenses()) {

                User user = expenseUser.getUser();

                balanceMap.putIfAbsent(user, 0.0);

                double currentBalance =
                        balanceMap.get(user);

                if (expenseUser.getExpenseUserType()
                        == ExpenseUserType.PAID) {

                    currentBalance += expenseUser.getAmount();

                } else {

                    currentBalance -= expenseUser.getAmount();
                }

                balanceMap.put(user, currentBalance);
            }
        }

        List<User> creditors = new ArrayList<>();
        List<User> debtors = new ArrayList<>();

        for (Map.Entry<User, Double> entry :
                balanceMap.entrySet()) {

            if (entry.getValue() > 0) {
                creditors.add(entry.getKey());
            }
            else if (entry.getValue() < 0) {
                debtors.add(entry.getKey());
            }
        }

        List<Transaction> transactions =
                new ArrayList<>();

        int i = 0;
        int j = 0;

        while(i < debtors.size()
                && j < creditors.size()) {

            User debtor = debtors.get(i);
            User creditor = creditors.get(j);

            double debtorAmount =
                    Math.abs(balanceMap.get(debtor));

            double creditorAmount =
                    balanceMap.get(creditor);

            double amount =
                    Math.min(
                            debtorAmount,
                            creditorAmount
                    );

            Transaction transaction =
                    new Transaction();

            transaction.setFromUserId(String.valueOf(debtor.getId()));
            transaction.setToUserId(String.valueOf(creditor.getId()));
            transaction.setAmount(amount);

            transactions.add(transaction);

            balanceMap.put(
                    debtor,
                    balanceMap.get(debtor) + amount
            );

            balanceMap.put(
                    creditor,
                    balanceMap.get(creditor) - amount
            );

            if(Math.abs(balanceMap.get(debtor)) < 0.01)
                i++;

            if(Math.abs(balanceMap.get(creditor)) < 0.01)
                j++;
        }

        return transactions;
    }
}