package org.nutrihealthplan.dietapp.dto;

public record ChangePasswordRequest
        (String email,
         String newPassword
        ) {
}
