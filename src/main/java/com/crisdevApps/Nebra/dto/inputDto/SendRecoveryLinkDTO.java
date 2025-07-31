package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.Email;

public record SendRecoveryLinkDTO(
        @Email String email
) {
}
