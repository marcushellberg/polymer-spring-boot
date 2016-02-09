package org.vaadin.marcus;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.marcus.domain.Role;
import org.vaadin.marcus.domain.User;
import org.vaadin.marcus.repository.UserRepository;

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

            @Override
            public void afterPropertiesSet() {
                addUser("admin", "admin");
                addUser("user", "user");
            }

            private void addUser(String username, String password) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                user.setRole(username.equals("admin") ? Role.ADMIN: Role.USER);
                userRepository.save(user);
            }
        };
    }
}
