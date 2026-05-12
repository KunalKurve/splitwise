package com.example.splitwise.models;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Group {

    @ManyToMany
    private List<User> members;

    @ManyToOne
    private User createdBy;

    @OneToMany
    private List<Expense> allExpenses;

    @ManyToMany
    private User admin;

}
