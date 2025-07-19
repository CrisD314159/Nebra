package com.crisdevApps.Nebra.mappers;

import com.crisdevApps.Nebra.dto.outputDto.GetCommentDTO;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.Comment;
import com.crisdevApps.Nebra.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "businessId", source = "business", qualifiedByName = "getBusinessId")
    @Mapping(target = "businessName", source = "business", qualifiedByName = "getBusinessName")
    @Mapping(target = "authorId", source = "author", qualifiedByName = "getAuthorId")
    @Mapping(target = "authorName", source = "author", qualifiedByName = "getAuthorName")
    GetCommentDTO toDto (Comment comment);

    @Named("getBusinessId")
    default UUID getBusinessId(Business business){
        return business.getId();
    }

    @Named("getBusinessName")
    default String getBusinessName(Business business){
        return business.getName();
    }

    @Named("getAuthorId")
    default UUID getAuthorId(User user){
        return user.getId();
    }

    @Named("getAuthorName")
    default String getAuthorName(User user){
        return user.getName();
    }
}
