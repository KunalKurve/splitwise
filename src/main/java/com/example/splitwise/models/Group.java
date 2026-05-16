package com.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_groups")
// @Entity(name = "userGroups") can name sql table name differently therefore use @Table
// "groups" name did not create table
//can't name it group because SQL has a reserved keyword "group"
public class Group extends BaseModel{

    private String description;
    private String groupName;

    /*
    Relationship wale Attributes
    */

    //@ManyToMany
    //private List<User> members;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    List<GroupMember> members;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @OneToMany(mappedBy = "group")
    private List<Expense> allExpenses;

    //@ManyToOne
    //@JoinColumn(name = "admin_id")
    //private User admin;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupAdmin> admins;

}
