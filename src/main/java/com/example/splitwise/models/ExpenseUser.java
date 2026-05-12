package com.example.splitwise.models;

import com.example.splitwise.models.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

@Getter
@Setter
@Entity(name = "expense_users")
public class ExpenseUser extends BaseModel{

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Expense expense;

    private Double amount;

    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Enumerated(value = EnumType.STRING)
    private ExpenseType expenseType;
}
