package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UpdateUserDTO(
        @NotEmpty UUID id,

       @NotBlank String name,

        MultipartFile profilePicture,

       @NotBlank String location
) {


}
