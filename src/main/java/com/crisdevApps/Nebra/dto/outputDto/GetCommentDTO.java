package com.crisdevApps.Nebra.dto.outputDto;

import java.util.UUID;

public record GetCommentDTO(
         UUID id,
         String title,
         int score,
         String content,
         UUID businessId,
         String businessName,
         UUID authorId,
         String authorName,
         String answer
) {
}
