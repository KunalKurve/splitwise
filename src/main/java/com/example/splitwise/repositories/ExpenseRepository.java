package com.example.splitwise.repositories;

import com.example.splitwise.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findAllByGroup_Id(int groupId);

//    @Query("""
//            SELECT e
//            FROM Expense e
//            WHERE e.group IS NULL
//            AND e.createdBy.id = :userId
//            """) - only fetches the non-group entries created by user
    @Query("""
        SELECT eu.expense
        FROM ExpenseUser eu
        WHERE eu.user.id = :userId
        AND eu.expense.group IS NULL
        """)
    List<Expense> findNonGroupExpensesForUser(@Param("userId") int userId);
}
