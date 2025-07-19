package com.crisdevApps.Nebra.dto.outputDto;

import com.crisdevApps.Nebra.model.Image;

import java.util.UUID;

public record GetUserProfileDTO(
        UUID id,

        String name,

        Image profilePicture,

        String email,

        String location,

        int businessAmount
) {

}
