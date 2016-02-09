package org.vaadin.marcus.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vaadin.marcus.domain.Expense;
import org.vaadin.marcus.domain.Status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    @RequestMapping(method = RequestMethod.GET)
    public List<Expense> getExpenses(){
        Expense expense = new Expense();
        expense.setMerchant("Spring Booty Inc");
        expense.setDate(new Date());
        expense.setComment("It was good");
        expense.setStatus(Status.NEW);
        expense.setTotal(new BigDecimal("103.45"));

        return Arrays.asList(expense);
    }

}
