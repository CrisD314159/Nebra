package com.crisdevApps.Nebra.dto.outputDto;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO (
        String token,
        String refresh
){
}