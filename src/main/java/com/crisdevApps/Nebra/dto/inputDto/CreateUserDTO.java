package com.crisdevApps.Nebra.dto.inputDto;

import com.crisdevApps.Nebra.model.Image;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateUserDTO(
        @NotBlank @Length(max = 50) String name,

        String profilePicture,


       @NotBlank @Email String email,

       @NotBlank @Length(min = 5)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._\\-])[A-Za-z\\d@$!%*?&._\\-]{8,}$",
                message = "The password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String password,

       @NotBlank @Length(max = 35) String location

) {


}
