package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import com.crisdevApps.Nebra.dto.inputDto.VerifyAccountDTO;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAccountService {
    void DeleteAccount(UUID userId);

    void ChangePassword(ChangePasswordDTO changePasswordDTO);

    void SendRecoveryLink(String email);

    void VerifyAccount(VerifyAccountDTO verifyAccountDTO);


}
