package com.crisdevApps.Nebra.mappers;

import com.crisdevApps.Nebra.dto.outputDto.GetReportDTO;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.Report;
import com.crisdevApps.Nebra.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {

    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserId")
    @Mapping(target = "userName", source = "user", qualifiedByName = "getUserName")
    @Mapping(target = "businessId", source = "business", qualifiedByName = "getBusinessId")
    @Mapping(target = "businessName", source = "business", qualifiedByName = "getBusinessName")
    GetReportDTO toDto(Report report);


    @Named("getUserId")
    default UUID getUserId(User user){
        return user.getId();
    }

    @Named("getUserName")
    default String getUserName(User user){
        return user.getName();
    }

    @Named("getBusinessId")
    default UUID getBusinessId(Business business){
        return business.getId();
    }

    @Named("getBusinessName")
    default String getBusinessName(Business business){
        return business.getName();
    }
}
