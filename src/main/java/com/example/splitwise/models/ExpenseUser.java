package com.example.splitwise.models;

import com.example.splitwise.models.enums.ExpenseType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

@Getter
@Setter
@Entity(name = "expense_users")
public class ExpenseUser {

    @ManyToOne
    private User user;

    @ManyToOne
    private Expense expense;

    private Double amount;

    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Enumerated(value = EnumType.STRING)
    private ExpenseType expenseType;
}
