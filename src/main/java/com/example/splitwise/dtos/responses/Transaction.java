package com.example.splitwise.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction{

    // Transaction is a Response and not a Model, hence it doesn't extends BaseModel
    // it is  response dto so returning objects like User might reveal sensitive info
    // private User fromUser;
    // private User toUser;

    private String fromUserId;
    private String fromUserName;
    private String toUserId;
    private String toUserName;
    private Double amount;

}
