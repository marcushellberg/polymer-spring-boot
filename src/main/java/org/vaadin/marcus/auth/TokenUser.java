package org.vaadin.marcus.auth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.vaadin.marcus.domain.Role;
import org.vaadin.marcus.domain.User;


public class TokenUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public TokenUser(User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }
}
