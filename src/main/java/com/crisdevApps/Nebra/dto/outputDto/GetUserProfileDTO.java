package com.crisdevApps.Nebra.dto.outputDto;

import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.enums.UserRole;

import java.util.UUID;

public record GetUserProfileDTO(
        UUID id,

        String name,

        Image profilePicture,

        String email,

        UserRole userRole,

        String location,

        int businessAmount
) {

}
