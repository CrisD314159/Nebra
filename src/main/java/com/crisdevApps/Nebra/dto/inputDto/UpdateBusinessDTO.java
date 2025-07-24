package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.Schedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record UpdateBusinessDTO(

        @NotEmpty UUID id,
        @NotBlank @Length(max = 400) String description,
        @NotBlank @Length(max = 200) String name,
        @NotEmpty @Length(max = 15) String phoneContact,
        List<String> imagesIds,
        double latitude,
        double longitude,
        @NotEmpty ArrayList<Schedule> schedules
) {
}
