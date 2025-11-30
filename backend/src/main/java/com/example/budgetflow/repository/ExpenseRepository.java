package com.example.budgetflow.repository;

import com.example.budgetflow.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(String userId);
    
    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND e.date >= :startDate")
    List<Expense> findByUserIdAndDateAfter(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.date >= :startDate")
    Double getTotalExpensesByUserIdAndDateAfter(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate);
}

