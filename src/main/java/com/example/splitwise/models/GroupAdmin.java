package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GroupAdmin extends BaseModel{

    @ManyToOne
    private Group group;

    @ManyToOne
    private User admin;

    @ManyToOne
    private User addedBy;
}
