package com.crisdevApps.Nebra.dto.outputDto;

import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.Schedule;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GetBusinessDTO(
        UUID id,
        String name,
        String description,
        List<Image> images,
        String phoneContact,
        BusinessCategory category,
        double latitude,
        double longitude,
        ArrayList<Schedule> scheduleList,
        int averageScore,
        UUID ownerId,
        String ownerName,
        BusinessState businessState
) {

}
