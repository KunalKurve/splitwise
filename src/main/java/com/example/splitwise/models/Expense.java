package com.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "expenses")
public class Expense extends BaseModel{

    private String description;

    private double amount;

    private Date addedAt;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    // composition (has-a)
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER)
    private List<ExpenseUser> userExpenses;
    /*
     instead of
     private Map<User, Double> receivers;
     private Map<User, Double> givers;
    */
}

