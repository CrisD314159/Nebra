package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CreateCommentDTO(
       @NotEmpty UUID id,
       @NotBlank String title,
       @NotBlank @Length(max = 200) String content,
       @PositiveOrZero @Max(5) int score
) {

}
