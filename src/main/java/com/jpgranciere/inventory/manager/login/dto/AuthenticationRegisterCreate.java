package com.jpgranciere.inventory.manager.login.dto;

import com.jpgranciere.inventory.manager.login.user.role.UserRole;

public record AuthenticationRegisterCreate(String login, String password, UserRole userRole) {
}
