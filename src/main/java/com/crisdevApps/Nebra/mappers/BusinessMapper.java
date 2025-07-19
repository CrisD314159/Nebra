package com.crisdevApps.Nebra.mappers;

import com.crisdevApps.Nebra.dto.inputDto.CreateBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetBusinessDTO;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.User;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessMapper {

    @Mapping(target = "images", ignore = true)
    Business toEntity(CreateBusinessDTO createBusinessDTO);

    @Mapping(target = "ownerId", source = "business.userOwner", qualifiedByName = "userId")
    @Mapping(target = "ownerName", source = "business.userOwner", qualifiedByName = "userName")
    @Mapping(target = "latitude", source = "business.location", qualifiedByName = "latitude")
    @Mapping(target = "longitude", source = "business.location", qualifiedByName = "longitude")
    @Mapping(target = "averageScore", source = "score")
    GetBusinessDTO toDTO(Business business, int score);


    @Named("userId")
    default UUID toUserId(User user){
        return user.getId();
    }

    @Named("latitude")
    default double toLatitude(Point point){
        return point.getX();
    }

    @Named("longitude")
    default double toLongitude(Point point){
        return point.getY();
    }

    @Named("userName")
    default String toUserName(User user){
        return user.getName();
    }
}
