package org.vaadin.marcus;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.marcus.domain.Expense;
import org.vaadin.marcus.domain.Role;
import org.vaadin.marcus.domain.Status;
import org.vaadin.marcus.domain.User;
import org.vaadin.marcus.repository.ExpensesRepository;
import org.vaadin.marcus.repository.UserRepository;

import java.math.BigDecimal;
import java.util.*;

@SpringBootApplication
@Configuration
@ComponentScan
public class PolymerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolymerDemoApplication.class, args);
    }

    @Bean
    public InitializingBean insertDefaultUsers() {
        return new InitializingBean() {
            @Autowired
            private UserRepository userRepository;
            @Autowired
            ExpensesRepository expensesRepository;

            @Override
            public void afterPropertiesSet() {
                addUser("admin", "admin");
                addUser("user", "user");

                generateExpenses();
            }

            private void generateExpenses() {
                int howMany = 200;
                ArrayList<Expense> expenses = new ArrayList<>(howMany);
                Random random = new Random();
                List<String> merchants = Arrays.asList("Office supplies", "Electronics", "Rental car", "Airline",
                        "Hotel", "Restaurant", "Taxi", "Ride sharing", "Fast food",
                        "Parking", "Breakfast", "Shuttle");
                User user = userRepository.findOneByUsername("user").orElseThrow(() -> new UsernameNotFoundException(""));
                long time = System.currentTimeMillis();
                long fiveDays = 5 * 24 * 60 * 60 * 1000;

                for (int i = 0; i < howMany; i++) {
                    time -= (random.nextDouble() * fiveDays);

                    Expense expense = new Expense();
                    expense.setUser(user);
                    expense.setMerchant(merchants.get(random.nextInt(merchants.size())));
                    BigDecimal total = new BigDecimal(random.nextDouble() * random.nextDouble() * 300 + 10);
                    expense.setTotal(total.setScale(2, BigDecimal.ROUND_HALF_DOWN));
                    expense.setDate(new Date(time));
                    expense.setComment("Expense from NYC business trip.");

                    if (i > 30) {
                        expense.setStatus(Status.REIMBURSED);
                    } else if (i > 15) {
                        expense.setStatus(Status.IN_PROGRESS);
                    } else {
                        expense.setStatus(Status.NEW);
                    }

                    expenses.add(expense);
                }

                expensesRepository.save(expenses);
            }

            private void addUser(String username, String password) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                user.setRole(username.equals("admin") ? Role.ADMIN : Role.USER);
                userRepository.save(user);
            }
        };
    }
}
