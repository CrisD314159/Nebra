package com.crisdevApps.Nebra.mappers;

import com.crisdevApps.Nebra.dto.outputDto.GetUserProfileDTO;
import com.crisdevApps.Nebra.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "businessAmount" , source = "businessAmount")
    GetUserProfileDTO toDTO(User user, int businessAmount);
}
