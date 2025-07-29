package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record AnswerCommentDTO (
        @NotNull UUID commentId,
        @NotBlank @Length(max = 200) String answer
        ){
}
