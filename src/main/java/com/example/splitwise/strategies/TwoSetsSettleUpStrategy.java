package com.example.splitwise.strategies;

import com.example.splitwise.dtos.responses.Transaction;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.User;
import com.example.splitwise.utils.ExpenseUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
public class TwoSetsSettleUpStrategy implements SettleUpStrategy{
    // HeapSettleUpStrategy

    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {

        Map<User, Double> userExpenses = ExpenseUtils.aggregateExpenses(expenses);

        PriorityQueue<Pair> minExpenseHeap = new PriorityQueue<>();
        PriorityQueue<Pair> maxExpenseHeap = new PriorityQueue<>(
                (p, q) -> Double.compare(q.amount, p.amount)
        );

        for (User user : userExpenses.keySet()){
            Double amount = userExpenses.get(user);
            if(amount < 0){
                minExpenseHeap.add(new Pair(user, amount));
            }
            else{
                maxExpenseHeap.add(new Pair(user, amount));
            }
        }

        List<Transaction> transactionsToBeDone = new ArrayList<>();
        while(!minExpenseHeap.isEmpty() && !maxExpenseHeap.isEmpty()){
            Pair minExpense = minExpenseHeap.poll();
            Pair maxExpense = maxExpenseHeap.poll();

            double amountToBeTransferred = 0d;
            if (minExpense!= null && maxExpense!= null) {
                amountToBeTransferred = Math.min(maxExpense.amount, Math.abs(minExpense.amount));

                Transaction transaction = new Transaction();
                transaction.setAmount(amountToBeTransferred);
                transaction.setFromUserId(String.valueOf(minExpense.user.getId()));
                transaction.setFromUserName(minExpense.user.getName());
                transaction.setToUserId(String.valueOf(maxExpense.user.getId()));
                transaction.setFromUserName(maxExpense.user.getName());
                transactionsToBeDone.add(transaction);
            }

            if(minExpense != null && minExpense.amount + amountToBeTransferred < 0){
                minExpense.amount += amountToBeTransferred;
                minExpenseHeap.add(minExpense);
            }

            if(maxExpense!= null && maxExpense.amount - amountToBeTransferred != 0){
                maxExpense.amount -= amountToBeTransferred;
                maxExpenseHeap.add(maxExpense);
            }

        }

        return transactionsToBeDone;
    }

    static class Pair implements Comparable<Pair>{

        User user;
        Double amount;

        Pair(User user, Double amount){
            this.user = user;
            this.amount = amount;
        }

        @Override
        public int compareTo(Pair o) {
            return Double.compare(this.amount, o.amount);
        }
    }
}
