package com.example.splitwise.commands;

import com.example.splitwise.controllers.ExpenseController;
import com.example.splitwise.dtos.requests.AddGroupExpenseRequestDto;
import com.example.splitwise.dtos.requests.AddUserExpenseRequestDto;
import com.example.splitwise.dtos.responses.AddGroupExpenseResponseDto;
import com.example.splitwise.dtos.responses.AddUserExpenseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddUserExpense implements Command{

    private final ExpenseController expenseController;
    private final String COMMAND_NAME = "AddUserExpense";

    @Autowired
    public AddUserExpense(ExpenseController expenseController){
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        String[] commandFragments = input.split(" ");
        if(commandFragments[0].equalsIgnoreCase(COMMAND_NAME)){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        String[] commandFragments = input.split(" ");
        int userId = Integer.parseInt(commandFragments[1]);
        double amount = Double.parseDouble(commandFragments[2]);

        AddUserExpenseRequestDto requestDto = new AddUserExpenseRequestDto();
        requestDto.setUserId(userId);
        requestDto.setAmount(amount);

        try {
            AddUserExpenseResponseDto responseDto = expenseController.addUserExpense(requestDto);
            System.out.println(responseDto.getExpenseId());
            System.out.println(responseDto.getResponseStatus());
        } catch (Exception e) {
            throw new RuntimeException("Something went Wrong !");
        }

    }
}
