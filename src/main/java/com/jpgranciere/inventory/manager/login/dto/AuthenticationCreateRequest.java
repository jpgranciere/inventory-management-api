package com.jpgranciere.inventory.manager.login.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthenticationCreateRequest(

        @NotBlank
        @Size(min = 3, max = 80)
        String login,

        @NotBlank
        @Size(min = 6, max = 72)
        String password) {
}
