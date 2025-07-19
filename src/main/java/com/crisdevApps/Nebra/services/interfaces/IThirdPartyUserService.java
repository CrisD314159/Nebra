package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.model.User;

public interface IThirdPartyUserService {
    TokenDTO LoginWithThirdParty(String email, String name, String profilePicture);
    User GetOrCreateValidUser(String email, String name, String profilePicture);
}
