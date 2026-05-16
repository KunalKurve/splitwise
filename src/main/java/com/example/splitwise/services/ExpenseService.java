package com.example.splitwise.services;

import com.example.splitwise.dtos.requests.PaidByDto;
import com.example.splitwise.dtos.requests.SplitRequestDto;
import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.*;
import com.example.splitwise.models.enums.Currency;
import com.example.splitwise.models.enums.ExpenseType;
import com.example.splitwise.models.enums.ExpenseUserType;
import com.example.splitwise.models.enums.SplitType;
import com.example.splitwise.repositories.*;
import com.example.splitwise.strategies.SplitStrategy;
import com.example.splitwise.strategies.SplitStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupAdminRepository groupAdminRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseUserRepository expenseUserRepository;
    private final SplitStrategyFactory splitStrategyFactory;

    @Autowired
    public ExpenseService(
            UserRepository userRepository,
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            GroupAdminRepository groupAdminRepository,
            ExpenseRepository expenseRepository,
            ExpenseUserRepository expenseUserRepository,
            SplitStrategyFactory splitStrategyFactory
    ){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupAdminRepository = groupAdminRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.expenseRepository = expenseRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.splitStrategyFactory = splitStrategyFactory;
    }

    @Transactional
    public Expense createGroupExpense(
            int groupId, int createdByUserId, String desc, SplitType splitType, double amount,
            List<PaidByDto> whoAllPaid, List<SplitRequestDto> splitRequests
            ) throws InvalidGroupException, InvalidUserException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new InvalidGroupException("Group not found"));

        User createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        groupMemberRepository.findByGroupIdAndUserId(groupId, createdByUserId)
                .orElseThrow(()-> new UnAuthorizedAccessException("The user is not a part of group"));

        /*
        Validate total paid amount
     */
        double totalPaidAmount = 0;

        for (PaidByDto paidByDto : whoAllPaid) {
            totalPaidAmount += paidByDto.getAmount();
        }

        if (totalPaidAmount != amount) {
            throw new RuntimeException(
                    "Total paid amount does not match expense amount"
            );
        }

        Expense expense = new Expense();
        expense.setGroup(group);
        expense.setDescription(desc);
        expense.setAmount(amount);
        expense.setExpenseType(ExpenseType.NORMAL);
        expense.setCreatedAt(new Date());
        expense.setCreatedBy(createdBy);
        Expense savedExpense = expenseRepository.save(expense);

        List<ExpenseUser> expenseUsers = new ArrayList<>();

        for(PaidByDto paidByDto : whoAllPaid){

            Integer userId = paidByDto.getUserId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new InvalidUserException(
                                    "User not found with id: " + userId
                            ));

            // User must belong to group
            groupMemberRepository
                    .findByGroupIdAndUserId(groupId, userId)
                    .orElseThrow(() ->
                            new UnAuthorizedAccessException(
                                    "User is not part of group"
                            ));

            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setUser(user);
            expenseUser.setAmount(paidByDto.getAmount());
            expenseUser.setCurrency(Currency.INR);
            expenseUser.setCreatedAt(new Date());
            expenseUser.setExpenseUserType(ExpenseUserType.PAID);
            expenseUser.setExpense(savedExpense);
            expenseUserRepository.save(expenseUser);
            expenseUsers.add(expenseUser);
        }

        SplitStrategy splitStrategy = splitStrategyFactory.getSplitStrategy(splitType);

        Map<Integer, Double> splitMap = splitStrategy.calculateSplits(
                amount, splitRequests);

        /*
        Save OWED entries
     */
        for (Map.Entry<Integer, Double> entry : splitMap.entrySet()) {

            Integer userId = entry.getKey();
            Double owedAmount = entry.getValue();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new InvalidUserException(
                            "User not found with id: " + userId));

            // User must belong to group
            groupMemberRepository
                    .findByGroupIdAndUserId(groupId, userId)
                    .orElseThrow(() ->
                            new UnAuthorizedAccessException(
                                    "User is not part of group"
                            ));

            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setExpense(savedExpense);
            expenseUser.setUser(user);
            expenseUser.setAmount(owedAmount);
            expenseUser.setExpenseUserType(ExpenseUserType.OWED);
            expenseUser.setCurrency(Currency.INR);
            expenseUser.setCreatedAt(new Date());
            expenseUserRepository.save(expenseUser);
            expenseUsers.add(expenseUser);
        }
        savedExpense.setUserExpenses(expenseUsers);
        return savedExpense;
    }

    @Transactional
    public Expense createUserExpense(int userId, double amount) throws InvalidUserException {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        Expense expense = new Expense();
        expense.setCreatedBy(user);
        expense.setCreatedAt(new Date());
        ExpenseType expenseType = amount > 0 ? ExpenseType.NORMAL : ExpenseType.PAYMENT;
        expense.setExpenseType(expenseType);
        expense.setDescription("New Payment");
        Expense savedExpense = expenseRepository.save(expense);

        ExpenseUser expenseUser = new ExpenseUser();
        expenseUser.setCreatedAt(new Date());
        expenseUser.setExpense(savedExpense);
        expenseUser.setUser(user);
        expenseUser.setAmount((double) amount);
        ExpenseUserType expenseUserType = amount > 0 ? ExpenseUserType.PAID : ExpenseUserType.OWED;
        expenseUser.setExpenseUserType(expenseUserType);
        expenseUser.setCurrency(Currency.INR);
        expenseUserRepository.save(expenseUser);

        return savedExpense;
    }

}
