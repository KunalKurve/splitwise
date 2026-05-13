package com.example.splitwise.services;

import com.example.splitwise.dtos.responses.Transaction;
import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.ExpenseRepository;
import com.example.splitwise.repositories.ExpenseUserRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import com.example.splitwise.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettleUpService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseUserRepository expenseUserRepository;
    private final SettleUpStrategy settleUpStrategy;

    @Autowired
    public SettleUpService(
            UserRepository userRepository,
            GroupRepository groupRepository,
            ExpenseRepository expenseRepository,
            ExpenseUserRepository expenseUserRepository,
            SettleUpStrategy settleUpStrategy
    ){
        this.userRepository = userRepository;
        this.groupRepository =  groupRepository;
        this.expenseRepository = expenseRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    public List<Transaction> settleGroup(int groupId) throws InvalidGroupException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new InvalidGroupException("Group not found"));

        List<Expense> expenses = expenseRepository.findAllByGroup_Id(group.getId());

        return settleUpStrategy.settleUp(expenses);
    }

    public List<Transaction> settleUser(int userId) throws InvalidUserException {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        List<Expense> expenses = expenseRepository.findNonGroupExpensesForUser(user.getId());

        return settleUpStrategy.settleUp(expenses);
    }
}
