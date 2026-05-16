package com.example.splitwise.commands;

import com.example.splitwise.controllers.ExpenseController;
import com.example.splitwise.dtos.requests.AddGroupExpenseRequestDto;
import com.example.splitwise.dtos.requests.PaidByDto;
import com.example.splitwise.dtos.requests.SplitRequestDto;
import com.example.splitwise.dtos.responses.AddGroupExpenseResponseDto;
import com.example.splitwise.models.enums.SplitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddGroupExpense implements Command {

    private final ExpenseController expenseController;
    private final String COMMAND_NAME = "AddGroupExpense";

    @Autowired
    public AddGroupExpense(ExpenseController expenseController){
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        String[] commandFragments = input.split(" ");
        return commandFragments[0].equalsIgnoreCase(COMMAND_NAME);
    }

    @Override
    public void execute(String input) {

        try {

            String[] commandFragments = input.split(" ");
            if(commandFragments.length != 7){
                throw new IllegalArgumentException(
                        "Usage: AddGroupExpense groupId description amount splitType paidBy participants"
                );
            }

            /*
             Command Format:

             AddGroupExpense
             groupId
             description
             totalAmount
             splitType
             paidBy
             participants

             Example:

             AddGroupExpense 1 Dinner 3000 EQUAL
             2:2000,3:1000
             2,3,4,5
            */

            Integer groupId = Integer.parseInt(commandFragments[1]);
            String description = commandFragments[2];
            Double totalAmount = Double.parseDouble(commandFragments[3]);
            SplitType splitType = SplitType.valueOf(commandFragments[4]);

            /*
             Parse paidBy

             Example:
             2:2000,3:1000
            */

            String paidByInput = commandFragments[5];
            String[] paidByArray = paidByInput.split(",");
            List<PaidByDto> paidByList = new ArrayList<>();

            for(String paidBy : paidByArray) {

                String[] paidInfo = paidBy.split(":");
                PaidByDto paidByDto = new PaidByDto();
                paidByDto.setUserId(Integer.parseInt(paidInfo[0]));
                paidByDto.setAmount(Double.parseDouble(paidInfo[1]));
                paidByList.add(paidByDto);
            }
            /*
             Parse participants

             Example:
             2,3,4,5
            */

            String participantsInput = commandFragments[6];
            String[] participantArray = participantsInput.split(",");
            List<SplitRequestDto> splitRequests = new ArrayList<>();
            for(String participant : participantArray) {

                SplitRequestDto splitRequest = new SplitRequestDto();
                splitRequest.setUserId(Integer.parseInt(participant));
                splitRequests.add(splitRequest);
            }

            AddGroupExpenseRequestDto requestDto = new AddGroupExpenseRequestDto();
            requestDto.setGroupId(groupId);
            requestDto.setDescription(description);
            requestDto.setSplitType(splitType);
            requestDto.setWhoAllPaid(paidByList);
            requestDto.setTotalAmount(totalAmount);
            requestDto.setSplitRequests(splitRequests);
            // Call controller
            AddGroupExpenseResponseDto responseDto = expenseController.addGroupExpense(requestDto);
            System.out.println(responseDto.getExpenseId());
            System.out.println(responseDto.getResponseStatus());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went Wrong !");
        }
    }

}
