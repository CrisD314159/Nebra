package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.NotBlank;

public record VerifyAccountDTO(
        @NotBlank String email,
        @NotBlank String code
) {
}
