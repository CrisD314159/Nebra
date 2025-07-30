package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.Schedule;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record UpdateBusinessDTO(

        @NotNull UUID id,
        @NotBlank @Length(max = 250) String description,
        @NotBlank @Length(max = 200) String name,
        @NotEmpty @Length(max = 15) String phoneContact,
        @NotEmpty List<String> imagesIds,
        @NotNull BusinessCategory category,
        double latitude,
        double longitude
) {
}
