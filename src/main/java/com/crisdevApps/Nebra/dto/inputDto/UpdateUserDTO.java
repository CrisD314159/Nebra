package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UpdateUserDTO(
       @NotBlank @Length(max = 50) String name,

        String profilePicture,

       @NotBlank @Length(max = 35) String location
) {


}
