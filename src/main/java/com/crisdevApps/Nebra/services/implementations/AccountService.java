package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.dto.inputDto.VerifyAccountDTO;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserState;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.security.JWTUtil;
import com.crisdevApps.Nebra.services.interfaces.IAccountService;
import com.crisdevApps.Nebra.services.interfaces.IEmailService;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements IAccountService {

    private final UserRepository userRepository;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IEmailService emailService;
    private final JWTUtil jwtUtil;

    @Override
    public void DeleteAccount(UUID userId){
        User user = userService.FindValidUserById(userId);

        user.setUserState(UserState.INACTIVE);

        userRepository.save(user);
    }

    @Override
    public void ChangePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userService.FindValidUserByEmail(changePasswordDTO.email());
        if(!jwtUtil.ValidateRecoverToken(changePasswordDTO.code(), user.getEmail()))
            throw new ValidationException("Code expired");

        if(!changePasswordDTO.code().equals(user.getRecoveryAccountToken()))
            throw new ValidationException("Invalid email or code");

        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        user.setRecoveryAccountToken(null);

        userRepository.save(user);
    }

    @Override
    public void SendRecoveryLink(String email) {
        User user = userService.FindValidUserByEmail(email);

        String jwtToken = jwtUtil.GenerateToken(user.getId(), user.getEmail(), false, null, user.getUserRole());

        user.setRecoveryAccountToken(jwtToken);

        String codeEncoded = URLEncoder.encode(jwtToken, StandardCharsets.UTF_8);

        emailService.SendEmail(new EmailDTO(
                "Reset password",
                "Here is your recovery link for your account at Nebra",
                user.getEmail(),
                user.getName(),
                "account/resetPassword?token="+ codeEncoded,
                "Reset password"
                ), "templates/generalEmailTemplate.html", false);

    }

    @Override
    public void VerifyAccount(VerifyAccountDTO verifyAccountDTO) {
        Optional<User> userOptional = userRepository.findByEmail(verifyAccountDTO.email());

        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }

        User user = userOptional.get();

        if(user.getUserState() == UserState.ACTIVE)
            throw new ValidationException("User is already verified");

        if(!user.getVerificationCode().equals(verifyAccountDTO.code()))
            throw new ValidationException("Invalid Email or code");

        user.setUserState(UserState.ACTIVE);
        user.setVerificationCode(null);
        userRepository.save(user);
    }
}
