package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CreateCommentDTO(
       @NotNull UUID businessId,
       @NotBlank @Length(max = 100) String title,
       @NotBlank @Length(max = 200) String content,
       @PositiveOrZero @Max(5) int score
) {

}
