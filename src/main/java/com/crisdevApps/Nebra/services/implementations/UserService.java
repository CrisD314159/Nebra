package com.crisdevApps.Nebra.services.implementations;


import com.crisdevApps.Nebra.dto.inputDto.*;
import com.crisdevApps.Nebra.dto.outputDto.*;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.UnauthorizedException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.mappers.UserMapper;
import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IEmailService;
import com.crisdevApps.Nebra.services.interfaces.IImageService;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IEmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final IImageService imageService;

    @Override
    public void SignUp(CreateUserDTO createUserDTO){

        if (UserExistsByEmail(createUserDTO.email())){
            throw new ValidationException("User already exists");
        }

        String verificationCode = String.valueOf(
                ThreadLocalRandom.current().nextInt(1000, 10000)
        );


        Image profilePicture;

        if(createUserDTO.profilePicture().isEmpty()){
            profilePicture = imageService.GetImage("nebraImages/krzunab8xoap7ox1baso");
        }else{
            profilePicture = imageService.GetImage(createUserDTO.profilePicture());
        }

        User user = User.builder()
                .password(passwordEncoder.encode(createUserDTO.password()))
                .name(createUserDTO.name())
                .email(createUserDTO.email())
                .location(createUserDTO.location())
                .userState(UserState.NOT_VERIFIED)
                .userRole(UserRole.USER)
                .isThirdPartyUser(false)
                .businessList(new ArrayList<>())
                .comments(new ArrayList<>())
                .favoriteBusiness(new ArrayList<>())
                .profilePicture(profilePicture)
                .reports(new ArrayList<>())
                .verificationCode(verificationCode)
                .build();


        userRepository.save(user);
        emailService.SendEmail(new EmailDTO(
                "Welcome to nebra",
                "Honestly is a pleasure to have you here. To verify your account, please enter this code when you log in to Nebra for the first time",
                user.getEmail(),
                user.getName(),
                verificationCode,
                ""
        ), "templates/verificationEmail.html", true);
    }

    @Override
    public String UploadUserProfilePicture(MultipartFile profilePicture) {
        if(profilePicture == null){
            throw new ValidationException("No image provided");
        }

        Image uploadedImage = imageService.UploadImage(profilePicture);

        return uploadedImage.getId();
    }


    private boolean UserExistsByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }


    @Override
    public void EditProfile(UpdateUserDTO updateUserDTO) {
        User user = FindValidUserById(updateUserDTO.id());

        Image profilePic = imageService.GetImage(updateUserDTO.profilePicture());

        user.setName(updateUserDTO.name());
        user.setLocation(updateUserDTO.location());
        user.setProfilePicture(profilePic);

        userRepository.save(user);

    }

    @Override
    public GetUserProfileDTO GetUserProfile(UUID userId){
        User user = FindValidUserById(userId);

        return userMapper.toDTO(user, user.getBusinessList().size());
    }

    @Override
    public List<GetUserProfileDTO> SearchUsers(String search, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> users = userRepository.findByNameIsLikeAndUserState(search, UserState.ACTIVE, pageable);

        return users.stream().map(user -> userMapper.toDTO(user, user.getBusinessList().size())).toList();

    }


    @Override
    public User FindValidUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new EntityNotFoundException("User not found");
        User userFound = user.get();
        IsUserValid(userFound);
        return userFound;
    }

    @Override
    public User FindValidUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new EntityNotFoundException("User not found");
        User userFound = user.get();
        IsUserValid(userFound);
        return user.get();
    }

    private void IsUserValid(User user){
        if(user.getUserState().equals(UserState.INACTIVE))
            throw new EntityNotFoundException("User not found");
        if(user.getUserState().equals(UserState.NOT_VERIFIED))
            throw new UnauthorizedException("User not found");
    }



}
