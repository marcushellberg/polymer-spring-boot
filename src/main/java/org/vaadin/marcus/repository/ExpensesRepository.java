package org.vaadin.marcus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.marcus.domain.Expense;
import org.vaadin.marcus.domain.User;

import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserOrderByDateDesc(User user);
}
