package com.example.splitwise.services;

import com.example.splitwise.dtos.responses.Transaction;
import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.models.enums.SettleUpStrategyType;
import com.example.splitwise.repositories.ExpenseRepository;
import com.example.splitwise.repositories.ExpenseUserRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import com.example.splitwise.strategies.SettleUpStrategy;
import com.example.splitwise.strategies.SettleUpStrategyFactory;
import com.example.splitwise.strategies.TwoSetsSettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettleUpService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;
    private final SettleUpStrategyFactory settleUpStrategyFactory;

    @Autowired
    public SettleUpService(
            UserRepository userRepository,
            GroupRepository groupRepository,
            ExpenseRepository expenseRepository,
            SettleUpStrategyFactory settleUpStrategyFactory
    ){
        this.userRepository = userRepository;
        this.groupRepository =  groupRepository;
        this.expenseRepository = expenseRepository;
        this.settleUpStrategyFactory = settleUpStrategyFactory;
    }

    public List<Transaction> settleGroup(int groupId, SettleUpStrategyType settleUpStrategyType) throws InvalidGroupException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new InvalidGroupException("Group not found"));

        List<Expense> expenses = expenseRepository.findAllByGroup_Id(group.getId());

        SettleUpStrategy settleUpStrategy = settleUpStrategyFactory.getSettleUpStrategy(settleUpStrategyType);

        return settleUpStrategy.settleUp(expenses);
    }

    public List<Transaction> settleUser(int userId, SettleUpStrategyType settleUpStrategyType) throws InvalidUserException {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        List<Expense> expenses = expenseRepository.findNonGroupExpensesForUser(user.getId());

        SettleUpStrategy settleUpStrategy = settleUpStrategyFactory.getSettleUpStrategy(settleUpStrategyType);

        return settleUpStrategy.settleUp(expenses);
    }
}
