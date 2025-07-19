package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.Session;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.repositories.SessionRepository;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.security.JWTUtil;
import com.crisdevApps.Nebra.services.interfaces.IThirdPartyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThirdPartyUser implements IThirdPartyUserService {

    private final JWTUtil jwtUtil;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;


    @Override
    public TokenDTO LoginWithThirdParty(String email, String name, String profilePicture) {
        User user = GetOrCreateValidUser(email, name, profilePicture);
        String token = jwtUtil.GenerateToken(user.getId(), user.getEmail(), false, null, user.getUserRole());
        String refreshToken = CreateSession(user);
        return new TokenDTO(token, refreshToken);
    }

    @Override
    public User GetOrCreateValidUser(String email, String name, String profilePicture) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) return userOptional.get();


        Image image = Image.builder().link(profilePicture).id(UUID.randomUUID().toString()).build();

        User user = User.builder()
                .password("ThirdPartyuser")
                .name(name)
                .email(email)
                .location("")
                .userState(UserState.ACTIVE)
                .userRole(UserRole.USER)
                .isThirdPartyUser(true)
                .businessList(new ArrayList<>())
                .comments(new ArrayList<>())
                .favoriteBusiness(new ArrayList<>())
                .profilePicture(image)
                .reports(new ArrayList<>())
                .verificationCode(null)
                .build();

        userRepository.save(user);
        return user;
    }

    private String CreateSession(User user){
        Session session = Session.builder()
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .user(user)
                .build();

        sessionRepository.saveAndFlush(session);
        return jwtUtil.GenerateToken(user.getId(), user.getEmail(), true, session.getId().toString(), user.getUserRole());

    }
}
