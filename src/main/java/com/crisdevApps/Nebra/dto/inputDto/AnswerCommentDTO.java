package com.crisdevApps.Nebra.dto.inputDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record AnswerCommentDTO (
        @NotEmpty UUID commentId,
        @NotBlank @Max(200) String answer
        ){
}
