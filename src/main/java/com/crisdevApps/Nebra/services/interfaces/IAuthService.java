package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.LoginDTO;
import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthService {
    TokenDTO Login(LoginDTO loginDTO);

    String CreateSession(User user);

    void Logout(String refresh);

    TokenDTO RefreshToken(String refresh);
}
