package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel{

    private String name;
    private String password;
    private String phone;

    @OneToMany(mappedBy = "createdBy")
    List<Expense> expenseHistory;
}
