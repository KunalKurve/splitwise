package com.example.splitwise.controllers;

import com.example.splitwise.dtos.requests.SettleGroupRequestDto;
import com.example.splitwise.dtos.requests.SettleUserRequestDto;
import com.example.splitwise.dtos.responses.ResponseStatus;
import com.example.splitwise.dtos.responses.SettleGroupResponseDto;
import com.example.splitwise.dtos.responses.SettleUserResponseDto;
import com.example.splitwise.dtos.responses.Transaction;
import com.example.splitwise.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {

    private SettleUpService settleUpService;

    @Autowired
    public SettleUpController(SettleUpService settleUpService){
        this.settleUpService = settleUpService;
    }

    public SettleGroupResponseDto settleUpGroup(SettleGroupRequestDto requestDto){
        SettleGroupResponseDto responseDto = new SettleGroupResponseDto();
        try{
            List<Transaction> transactions = settleUpService.settleGroup(
                    requestDto.getGroupId()
            );
            responseDto.setTransactions(transactions);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setMessage("Suggested Transactions!");
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
            responseDto.setMessage("Something went wrong!");
        }
        return responseDto;
    }

    public SettleUserResponseDto settleUpUser(SettleUserRequestDto requestDto){
        SettleUserResponseDto responseDto = new SettleUserResponseDto();
        try{
            List<Transaction> transactions = settleUpService.settleUser(
                    requestDto.getUserId()
            );
            responseDto.setTransactions(transactions);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setMessage("User Expenses have been Settled!");
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILED);
            responseDto.setMessage("User Expenses couldn't be Settled!");
        }
        return responseDto;
    }
}
