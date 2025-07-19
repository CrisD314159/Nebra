package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Schedule;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;

public record CreateBusinessDTO(
        @NotBlank String description,
        @NotBlank String name,
        @NotEmpty String phoneContact,
        BusinessCategory category,
        double latitude,
        double longitude,
        @NotEmpty ArrayList<Schedule> scheduleList
) {

}
