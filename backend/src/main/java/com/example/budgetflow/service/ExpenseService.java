package com.example.budgetflow.service;

import com.example.budgetflow.model.Expense;
import com.example.budgetflow.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    public List<Expense> getExpensesByUserId(String userId) {
        return expenseRepository.findByUserId(userId);
    }
    
    public List<Expense> getExpensesByUserIdThisMonth(String userId) {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return expenseRepository.findByUserIdAndDateAfter(userId, startOfMonth);
    }
    
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
    
    public Expense createExpense(Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(LocalDateTime.now());
        }
        if (expense.getCreatedAt() == null) {
            expense.setCreatedAt(LocalDateTime.now());
        }
        return expenseRepository.save(expense);
    }
    
    public Optional<Expense> updateExpense(Long id, Expense expense) {
        return expenseRepository.findById(id)
                .map(existingExpense -> {
                    existingExpense.setAmount(expense.getAmount());
                    existingExpense.setCategory(expense.getCategory());
                    existingExpense.setDescription(expense.getDescription());
                    existingExpense.setDate(expense.getDate());
                    return expenseRepository.save(existingExpense);
                });
    }
    
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Double getTotalExpensesThisMonth(String userId) {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        Double total = expenseRepository.getTotalExpensesByUserIdAndDateAfter(userId, startOfMonth);
        return total != null ? total : 0.0;
    }
}

