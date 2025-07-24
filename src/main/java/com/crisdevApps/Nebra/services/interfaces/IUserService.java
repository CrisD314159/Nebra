package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.UpdateUserDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateUserDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetUserProfileDTO;
import com.crisdevApps.Nebra.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserService{

    void SignUp(CreateUserDTO createUserDTO);

    String UploadUserProfilePicture(MultipartFile profilePicture);

    void EditProfile(UpdateUserDTO updateUserDTO);


    GetUserProfileDTO GetUserProfile(UUID userId);

    List<GetUserProfileDTO> SearchUsers(String search, int page);

    User FindValidUserByEmail(String email);

    User FindValidUserById(UUID id);
}
