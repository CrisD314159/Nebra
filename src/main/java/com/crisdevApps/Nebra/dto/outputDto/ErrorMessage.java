package com.crisdevApps.Nebra.dto.outputDto;

public record ErrorMessage<T>(
        boolean success,
        T message
) {
}
