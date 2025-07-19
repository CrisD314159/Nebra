package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.LoginDTO;
import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.UnauthorizedException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.model.Session;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.security.JWTUtil;
import com.crisdevApps.Nebra.services.interfaces.IAuthService;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import com.crisdevApps.Nebra.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final SessionRepository sessionRepository;
    private final JWTUtil jwtUtil;
    @Override
    public TokenDTO Login(LoginDTO loginDTO) {
        User user = userService.FindValidUserByEmail(loginDTO.email());

        if(user.isThirdPartyUser()) throw  new ValidationException("Use your Google account to sign in");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
        );

        if(authentication.isAuthenticated()){
            String token = jwtUtil.GenerateToken(user.getId(), user.getEmail(), false, null, user.getUserRole());
            String refresh = CreateSession(user);

            return new TokenDTO(token, refresh);
        }

        throw new UnauthorizedException("Invalid Credentials");
    }


    @Override
    public String CreateSession(User user) {
        Session session = Session.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();
        sessionRepository.saveAndFlush(session);

        return jwtUtil.GenerateToken(user.getId(), user.getEmail(),
                true, session.getId().toString(), user.getUserRole());
    }

    @Override
    public void Logout(String refresh) {
        UUID sessionId = jwtUtil.GetSessionIdFromRefreshToken(refresh);
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if(sessionOptional.isEmpty()) throw new EntityNotFoundException("Session not found");
        sessionRepository.delete(sessionOptional.get());
    }

    @Override
    public TokenDTO RefreshToken(String refresh) {
        UUID sessionId = jwtUtil.GetSessionIdFromRefreshToken(refresh);
        if(sessionId == null) {
            throw new ValidationException("Invalid refresh token");
        }

        Session session = sessionRepository.findById(sessionId).orElseThrow(
                ()-> new EntityNotFoundException("Session not found")
        );

        if(session.getExpiresAt().isBefore(LocalDateTime.now())) {
            sessionRepository.delete(session);
            throw new UnauthorizedException("Session expired");
        }

        User sessionUser = session.getUser();
        if (Duration.between(LocalDateTime.now(), session.getExpiresAt()).toDays() <= 2) {
            session.setExpiresAt(LocalDateTime.now().plusDays(7));
            sessionRepository.save(session);
            String token = jwtUtil.GenerateToken(sessionUser.getId(), sessionUser.getEmail(), false, null, sessionUser.getUserRole());
            String refreshToken = jwtUtil.GenerateToken(sessionUser.getId(), sessionUser.getEmail(),
                    true, session.getId().toString(), sessionUser.getUserRole());

            return new TokenDTO(token, refreshToken);
        }else{
            String token = jwtUtil.GenerateToken(sessionUser.getId(), sessionUser.getEmail(), false, null, sessionUser.getUserRole());
            return new TokenDTO(token, null);
        }
    }


}
