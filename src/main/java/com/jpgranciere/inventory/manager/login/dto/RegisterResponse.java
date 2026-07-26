package com.jpgranciere.inventory.manager.login.dto;

import com.jpgranciere.inventory.manager.login.user.entity.User;
import com.jpgranciere.inventory.manager.login.user.role.UserRole;

public record RegisterResponse(String id, String login, UserRole userRole) {
    public RegisterResponse(User user) {
        this(user.getId(), user.getLogin(), user.getRole());
    }
}
