package com.example.splitwise.models;

import com.example.splitwise.models.enums.Currency;
import com.example.splitwise.models.enums.ExpenseUserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExpenseUser extends BaseModel{

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Expense expense;

    private Double amount;

    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Enumerated(value = EnumType.ORDINAL)
    private ExpenseUserType expenseUserType;
}
