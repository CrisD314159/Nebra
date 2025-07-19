package com.crisdevApps.Nebra.dto.inputDto;

public record EmailDTO (
        String subject,
        String message,
        String to,
        String receiverName,
        String frontPathOrCode,
        String buttonName
) {
}
