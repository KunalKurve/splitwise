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

    @Query("select e from Expense e where e.group is null and e.user.id = :userId")
    List<Expense> findNonGroupExpensesForUser(@Param("userId") int userId);
}
