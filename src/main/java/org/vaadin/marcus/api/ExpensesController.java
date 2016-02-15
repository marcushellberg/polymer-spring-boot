package org.vaadin.marcus.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.vaadin.marcus.auth.UserAuthentication;
import org.vaadin.marcus.domain.Expense;
import org.vaadin.marcus.domain.Status;
import org.vaadin.marcus.domain.User;
import org.vaadin.marcus.repository.ExpensesRepository;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    @Autowired
    ExpensesRepository expensesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Expense> getExpenses() {
        return expensesRepository.findByUserOrderByDateDesc(getCurrentUser());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createExpense(@RequestBody Expense expense) {
        expense.setStatus(Status.NEW);
        expense.setUser(getCurrentUser());
        expensesRepository.save(expense);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<Void> updateExpense(@PathVariable Long id, @RequestBody Expense newExpense) {
        Expense expense = expensesRepository.findOne(id);

        if (expense.getUser().equals(getCurrentUser())) {
            expense.setDate(newExpense.getDate());
            expense.setMerchant(newExpense.getMerchant());
            expense.setComment(newExpense.getComment());
            expense.setTotal(newExpense.getTotal());

            expensesRepository.save(expense);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        Expense expense = expensesRepository.findOne(id);

        if (expense.getUser().equals(getCurrentUser()) && expense.getStatus() == Status.NEW) {
            expensesRepository.delete(expense);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private User getCurrentUser() {
        return ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails().getUser();
    }
}
