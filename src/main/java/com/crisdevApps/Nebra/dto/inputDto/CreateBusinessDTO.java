package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Schedule;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

public record CreateBusinessDTO(
        @NotBlank @Length(max = 400) String description,
        @NotBlank @Length(max = 200) String name,
        @NotEmpty @Length(max = 15) String phoneContact,
        @NotEmpty List<String> imagesIds,
        @NotNull BusinessCategory category,
        double latitude,
        double longitude
) {

}
