package com.crisdevApps.Nebra.dto.outputDto;

import java.util.UUID;

public record GetReportDTO(
        UUID id,
        UUID userId,
        String userName,
        UUID businessId,
        String businessName,
        String reason,
        String answer

) {
}
