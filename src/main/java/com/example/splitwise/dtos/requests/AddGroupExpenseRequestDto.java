package com.example.splitwise.dtos.requests;

import com.example.splitwise.models.enums.SplitType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddGroupExpenseRequestDto {

    private int groupId;
    private int createdByUserId;
    private String description;
    private double totalAmount;
    private List<PaidByDto> whoAllPaid;
    private SplitType splitType;
    private List<SplitRequestDto> splitRequests;
}
