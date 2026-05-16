package com.example.splitwise.controllers;

import com.example.splitwise.dtos.requests.AddGroupExpenseRequestDto;
import com.example.splitwise.dtos.requests.AddUserExpenseRequestDto;
import com.example.splitwise.dtos.responses.AddGroupExpenseResponseDto;
import com.example.splitwise.dtos.responses.AddUserExpenseResponseDto;
import com.example.splitwise.dtos.responses.ResponseStatus;
import com.example.splitwise.models.Expense;
import com.example.splitwise.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(
            ExpenseService expenseService
    ){
        this.expenseService = expenseService;
    }

    public AddUserExpenseResponseDto addUserExpense(AddUserExpenseRequestDto requestDto){
        AddUserExpenseResponseDto responseDto = new AddUserExpenseResponseDto();
        try {
            Expense expense = expenseService.createUserExpense(
                    requestDto.getUserId(),
                    requestDto.getAmount()
            );
            responseDto.setExpenseId(expense.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }

    public AddGroupExpenseResponseDto addGroupExpense(AddGroupExpenseRequestDto requestDto){
        AddGroupExpenseResponseDto responseDto = new AddGroupExpenseResponseDto();
        try {
            Expense expense = expenseService.createGroupExpense(
                    requestDto.getGroupId(),
                    requestDto.getCreatedByUserId(),
                    requestDto.getDescription(),
                    requestDto.getSplitType(),
                    requestDto.getTotalAmount(),
                    requestDto.getWhoAllPaid(),
                    requestDto.getSplitRequests()
            );
            responseDto.setExpenseId(expense.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
        }
        return responseDto;
    }
}
