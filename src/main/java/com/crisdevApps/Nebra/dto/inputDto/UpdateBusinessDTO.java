package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.Schedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.UUID;

public record UpdateBusinessDTO(

        @NotEmpty UUID id,
        @NotBlank String description,
        @NotBlank String name,
        @NotEmpty String phoneContact,
        ArrayList<Image> images,
        double latitude,
        double longitude,
        @NotEmpty ArrayList<Schedule> schedules
) {
}
