package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "expenses")
public class Expense {

    private String description;

    private double amount;

    private Date addedAt;

    @ManyToOne
    private User createdBy;

    // composition (has-a)
    @ManyToOne
    private Group group;

    @OneToMany
    private List<ExpenseUser> userExpenses;
    /*
     instead of
     private Map<User, Double> receivers;
     private Map<User, Double> givers;
    */
}

