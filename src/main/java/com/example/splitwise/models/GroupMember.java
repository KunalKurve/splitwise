package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class GroupMember {

    @ManyToOne
    private Group group;

    @ManyToOne
    private User member;

    @ManyToOne
    private User addedBy;

    private Date addedAt;
}
