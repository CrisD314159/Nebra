package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordDTO(
        @NotBlank @Length(min=5) String newPassword,
        @NotBlank String code,
        @NotBlank String email
) {
}
